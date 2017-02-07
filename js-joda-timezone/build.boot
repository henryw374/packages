(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.0.0")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/js-joda-timezone
       :version     +version+
       :description "A date and time library for javascript"
       :url         "https://js-joda.github.io/js-joda-timezone/"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    #_(download :url (format "https://github.com/js-joda/js-joda-timezone/archive/v%s.zip" +lib-version+)
                :unzip true)
    ; Can't simply do this because of 'Parse error. identifier is a reserved word for \'char\''
    #_(sift :move {#"^js-joda-timezone-([\d\.]+)/dist/js-joda-timezone\.js$" "cljsjs/js-joda/development/js-joda-timezone.inc.js"})
    (minify :in  "cljsjs/js-joda-timezone/development/js-joda-timezone.inc.js"
            :out "cljsjs/js-joda-timezone/production/js-joda-timezone.min.inc.js")
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.js-joda-timezone")
    (pom)
    (jar)))
