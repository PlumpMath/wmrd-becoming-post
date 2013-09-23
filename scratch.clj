(ns user
  (:require (me.raynes [fs :as fs]
                       [conch :as sh])))

(.getFile (clojure.java.io/resource "genavi.sh"))

(sh/programs bash pwd ls)

(pwd)

(ls "-l" {:seq true})



(sh/with-programs [ls]
  (ls {:seq true :verbose true}))

(sh/let-programs
 [produce-seq (.getFile (clojure.java.io/resource "produceSeq.sh"))
  genavi (.getFile (clojure.java.io/resource "genavi.sh"))]


 )


(fs/glob "README*")

(binding [fs/*cwd* (fs/expand-home "~/Desktop")]
  (fs/glob "*.mov"))
