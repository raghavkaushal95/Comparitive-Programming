-module(exchange).
-export([start/0,master_proc/0]).

start() ->
	spawn(exchange,master_proc,[]).


master_proc() -> 
	{ok,Callers} = file:consult("calls.txt"),
	io:format("** Calls to be made **~n"),
	print_callers(Callers),
	io:format("~n~n"),
	start_calling_processes(Callers),
	send_ready_message(Callers),
	process_messages().

print_callers([]) -> ok;

print_callers([{Caller,Callees} | T]) ->
	io:format("~s: ~p ~n",[Caller,Callees]),
	print_callers(T).

start_calling_processes([]) -> ok;

start_calling_processes([{Caller,Callees} | T]) ->
  Pid = spawn(calling,calling_proc,[{Caller,Callees,self()}]),
  register(Caller,Pid),
  start_calling_processes(T).


send_ready_message([]) -> ok;

send_ready_message([{Caller,_} | T]) ->
  Caller ! ready,
  send_ready_message(T).

process_messages() -> 
	receive
		{intro,From,To,Microsec} -> 
			io:format("~s received intro message from ~s [~b] ~n",[To,From,Microsec]),
			process_messages();
		{reply,From,To,Microsec} ->
			io:format("~s received reply message from ~s [~b] ~n",[To,From,Microsec]),
			process_messages();
		{no_messages,From} ->
			io:format("~nProcess ~s has received no calls for 1 second, ending... ~n",[From]),
			process_messages()
	after
		1500 -> io:format("~nMaster has received no replies for 1.5 seconds, ending... ~n")
	end.