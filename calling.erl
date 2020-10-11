-module(calling).
-export([calling_proc/1]).

calling_proc({Caller,Callees,Master}) ->
	ok = receive
		ready -> ok
	end,
	send_intro(Caller,Callees),
	receive_messages(Caller,Master).

send_intro(_,[]) -> ok;

send_intro(Caller,[Callee | T]) ->
	{_,_,Timestamp} = now(),
	Callee ! {intro,Caller,Timestamp},
	send_intro(Caller,T).

receive_messages(Caller,Master) ->
	receive
		{intro,From,Timestamp} -> 
			intro_message_received(From,Caller,Master,Timestamp),
			send_reply(From,Caller,Timestamp),
			receive_messages(Caller,Master);
		{reply,From,Timestamp} ->
			reply_message_received(From,Caller,Master,Timestamp),
			receive_messages(Caller,Master)

	after
		1000 -> no_messages_received(Caller,Master)
	end.

intro_message_received(From,To,Master,Timestamp) ->
	Master ! {intro,From,To,Timestamp}.

reply_message_received(From,To,Master,Timestamp) ->
	Master ! {reply,From,To,Timestamp}.

no_messages_received(Caller,Master) ->
	Master ! {no_messages,Caller}.
	
send_reply(To,Caller,Timestamp) ->
	To ! {reply,Caller,Timestamp}.
