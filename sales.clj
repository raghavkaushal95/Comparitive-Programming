(ns sales
(:require [clojure.string :only [index-of]]))


(defn Permu
 ; "creates a sequence of all permutat ions of multiple sequences"
  [CurrentSeq & rest-seqs]
  (if (nil? rest-seqs)
    (map #(cons % '()) CurrentSeq)
    (reduce concat (map (fn [CurrentSeq-val] (map #(cons CurrentSeq-val %)
(apply Permu rest-seqs))) CurrentSeq)))) 
 
(defmacro Foreach1
 ; "Uses reduce to implement doseq"
  [forms & body]
  (let [FormPair (partition 2 forms)
        vars (vec (map #(first %) FormPair))
        assignments (map #(second %) FormPair)]
    `(reduce (fn ~'[nothing args] (apply (fn ~vars ~@body) ~'args))
             nil
             (Permu ~@assignments)))) 



 
(defn fileInput []
  (def file1read(clojure.java.io/resource "cust.txt"))
  (def cust(slurp file1read))
   ;(def cust1 cust) 
   

  
  (def file2read (clojure.java.io/resource "prod.txt"))
  (def prod(slurp file2read))
  ;(def prod2 prod)
 
  
 (def file3read (clojure.java.io/resource "sales.txt"))
  (def sales(slurp file3read))
  ;(def sales2 sales)
)
       (def cust-id) 
       (def product-id)
       (def item-count) 
       (def sales-struct)




(def DataBase [])
(def DataId [])

  (defn InitialiseDatabase []
      
      
        
      (def CustomerName [])
      (def Product_Name [])
     
   )
  
(defn OrSplit [a1]
   
   (def DataBase [])
   (Foreach1 [string1 a1]
     (def str2 (clojure.string/split string1 #"\|"))
     (def z (conj DataBase str2 ))
     (def DataBase z)
   );(println DB)  
  DataBase
 )  
  
  (defn Delimiter [a1]
    
    (def str1 (clojure.string/split-lines a1))
      (OrSplit str1)
    )
  
  
(defn open []  
   (def must-id) 
       (def prodct-idd)
       (def itemer-cosdunt) 
       (def saleswer-sstruct)

  )
  
  
  
  
 
  



(defn readFile []
  (def CustomerData [])
 (def CustomerData (Delimiter cust))
 ;(println CustomerData)
 
  (def ProductData  [])
 (def ProductData (Delimiter prod))
 ;(println ProductData)
 
 (def SalesData [])
 (def SalesData (Delimiter sales))
 ;(println SalesData)
)



(defn func1 []    
 
           ( Foreach1 [i SalesData] 
             (def cust-id (get i 1))
             (def product-id (get i 2))
             (def item-count (get i 3)) 
             (def sales-struct(get i 0))
             
           )                 
  
 ) 
  
(defn func2 []
    (func1)  
          (Foreach1 [i SalesData]     
               (println (get i 0)":" 
                  (get (get CustomerData (- (read-string (get i 1)) 1)) 1)""
                  (get (get ProductData (- (read-string (get i 2)) 1)) 1)""
                   (get i 3)    
               )   
           )
) 

(defn parameterised []
      
   
   (def id_purchase 7)
   (def id id_purchase)
     (def temp 1)
   (def x temp)
   (def z false)
   (def supportqty z)
   (println "Purchase Info\n-------------------- ")
   (while (= z false)
             (println  x)":"  
   )
   
   (def support 6) 
   (def  temp_1 support)
)











(defn ReadUserName[]
  (Foreach1 [c CustomerData] 
    (def temp_name (get c 1)) 
    (def temp1 (conj CustomerName temp_name))
    (def  CustomerName temp1)
 )
  CustomerName
 )




(defn make-adder [x]
  (let [y x]
    (fn [z] (+ y z))))
(def add2 (make-adder 2))
(add2 4)







(defn customerTotal [string5]
  (def tempPos (+ 1 (.indexOf CustomerName string5) ))
  (def CustPos tempPos)
  (println "Customer Id is " CustPos)
  (def TotalPrice 0.0)
  (Foreach1 [sd SalesData] 
   ; (println "in the doseq statment")
    (if(=  (read-string (get sd 1)) CustPos     )
      (do
        
                 (def price (read-string(get (get ProductData (- (read-string(get sd 2)) 1)) 2)))
    

         (def numberofitems (read-string (get sd 3)))
       
                                
        (def temp_price (* price numberofitems   ))
     
       
      (def TotalPrice (+ TotalPrice temp_price))
      )
    )
  )
  TotalPrice
)


(defn mainopt []
  (def comm ("Customer DataBase for In"))

  (def string (clojure.string/split comm #"\d+"))
  (doseq [string1 string]
    (print (first string1))
    (println ":[ ]:")
    )
  )











  (defn TotalSalesCalculation []
    (ReadUserName)
    (def k true)
    (println  "Input customer name ") 
     (def ReadName (read-line))
        ;(println ReadName)
        (if(some #(= ReadName %) CustomerName)
       (println ReadName ":" (format "%.2f" (customerTotal ReadName))"$")                 
        (do
         (println "Record of Customer Not Found")
          (recur) 
        )
      )
    )
  

 




(defn choice []
  
  (fileInput)
  (InitialiseDatabase)
  (readFile)
  
  
  ;(MenuDisplay);cannot show menu here
  
  (println "\n\n\t Sales Menu \t \n------------------\n\n1. Show Customer Data\n 2. Show Product Data\n3. Show Sales Table\n4.Total Sales Table\n5.Total Count for Product\n6.Exit\n")
  
  (println  "Enter an option?") 
  (def keyVal (read-line))
  ; (loop [options (read-line)]
   (def x keyVal)
  
  
  (case x "6" 
             (println "Exit")
    "1" (do(doseq [i CustomerData] (println (first i) "["  ":" (next i)"]"))(choice))
    "2" (do(doseq [k ProductData] (println (first k) ": " "["(next k)"]"))(choice))
    "3" (do(func2)(choice))
    "4" (do(TotalSalesCalculation)(choice))
    "5" (do(println "Error")(choice))
   (do(println "Error")(choice))
    )
  ;)
)
(choice)


















