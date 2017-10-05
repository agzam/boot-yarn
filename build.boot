(set-env!
  :dependencies '[[org.clojure/clojure "1.8.0"]
                  [boot/core "2.7.1"]
                  [adzerk/bootlaces "0.1.13" :scope "test"]
                  [cheshire "5.7.1"]
                  [degree9/boot-semver "1.4.4" :scope "test"]
                  [degree9/boot-exec "1.0.0"]]
  :resource-paths #{"src"})

(require
  '[adzerk.bootlaces :refer :all]
  '[degree9.boot-semver :refer :all])

(task-options!
  target {:dir #{"target"}}
  pom {:project     'ag/boot-yarn
       :version     (get-version)
       :description "boot-clj task for managing npm modules with yarn"
       :url         "https://github.com/agzam/boot-yarn"
       :scm         {:url "https://github.com/agzam/boot-yarn"}})

(deftask develop
  "Build boot-yarn for development."
  []
  (comp
    (watch)
    (version :develop true
             :minor 'inc
             :patch 'zero
             :pre-release 'snapshot)
    (target)
    (build-jar)))

(deftask deploy
  "Build boot-yarn and deploy to clojars."
  []
  (comp
    (version)
    (target)
    (build-jar)
    (push-release)))
