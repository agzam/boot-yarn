(ns ag.quiet
  (:require [boot.core :refer :all]))

;; the following code was borrowed from
;; https://github.com/degree9/boot-semgit/blob/master/src/degree9/boot_semgit/workflow.clj#L7-L37

(def ^:dynamic *orig-err* nil)
(def ^:dynamic *orig-out* nil)
(def ^:dynamic *debug* false)

(deftask mute
  "Hide future task output."
  []
  (fn [next-handler]
    (fn [fileset]
      (binding [*orig-out* *out*
                *orig-err* *err*
                *out*      (new java.io.StringWriter)
                *err*      (new java.io.StringWriter)]
        (next-handler fileset)))))

(deftask unmute
  "Show future task output."
  []
  (fn [next-handler]
    (fn [fileset]
      (if (and *orig-out* *orig-err*)
        (binding [*out*      *orig-out*
                  *err*      *orig-err*
                  *orig-out* nil
                  *orig-err* nil]
          (next-handler fileset))
        (next-handler fileset)))))

(defmacro with-quiet [& tasks]
  `(if *debug* (comp ~@tasks) (comp (mute) ~@tasks (unmute))))
