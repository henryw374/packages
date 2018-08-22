(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.0.0")
(def +version+ (str +lib-version+ "-2"))

(task-options!
  push {:repo "clojars"}
 pom  {:project     'henryw374/js-joda-locale-en-us
       :version     +version+
       :description "prebuilt en-US locale addon for js-joda. Will go to a cljsjs package when the underlying is on npm"
       :url         "https://js-joda.github.io/js-joda/js-joda-locale"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (format "https://raw.githubusercontent.com/js-joda/js-joda-locale/8e384141cb191c8cbf35b2844cab15ace035f806/packages/en-us/dist/index.js" ))
    (sift :move {#"^index\.js$" "henryw374/js-joda-locale-en-us/development/js-joda-locale-en-us.inc.js"})
    
    (minify :in  "henryw374/js-joda-locale-en-us/development/js-joda-locale-en-us.inc.js"
            :out "henryw374/js-joda-locale-en-us/production/js-joda-locale-en-us.min.inc.js")
    (sift :include #{#"^henryw374"})
    (deps-cljs 
      :name "henryw374.js-joda-locale-en-us" 
      :requires ["cljs.js-joda"])
    (pom)
    (jar)))
