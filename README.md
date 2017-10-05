# boot-npm
[![Clojars Project](https://img.shields.io/clojars/v/ag/boot-yarn.svg)](https://clojars.org/ag/boot-yarn)
[![Dependencies Status](https://jarkeeper.com/ag/boot-yarn/status.svg)](https://jarkeeper.com/ag/boot-yarn)
[![Downloads](https://jarkeeper.com/ag/boot-yarn/downloads.svg)](https://jarkeeper.com/ag/boot-yarn)

Yarn (npm package manager) wrapper for [boot-clj][1].
_this lib is based on work done in https://github.com/degree9/boot-npm_

* Provides `yarn` task for installing node modules.
* Provides `exec` task for executing node modules. (auto-installs local module)

> The following outlines basic usage of the task, extensive testing has not been done.
> Please submit issues and pull requests!

## Usage

Add `boot-yarn` to your `build.boot` dependencies and `require` the namespace:

```clj
(set-env! :dependencies '[[ag/boot-yarn "X.Y.Z" :scope "test"]])
(require '[ag.boot-yarn :refer [yarn]])
```

Install a Node Module:

```clojure
(boot/deftask bower
  "Install bower to node_modules."
  []
  (yarn :add {:bower "latest"})))
```

##Task Options

The `yarn` task exposes a few options when using npm as part of a build process.

```clojure
[p package     VAL     str      "An edn file containing a package.json map."
 a add         FOO=BAR {kw str} "Dependency map."
 d develop             bool     "Include development dependencies with packages."
 r dry-run             bool     "Report what changes npm would have made. (usefull with boot -vv)"
 g global              bool     "Opperates in global mode. Packages are installed to prefix."
 c cache-key   VAL     kw       "Optional cache key for when npm is used with multiple dependency sets."
 _ include             bool     "Include package.json in fileset output."
 _ pretty              bool     "Pretty print generated package.json file"]
 s silent              bool     "No output"
```

The `:install` option is provided for installing node modules, takes a map containing a dependency/version pair. This will install the module to a temporary `node_modules` folder and include this folder in the fileset output.

```clojure
(boot/deftask bower
  "Install bower to node_modules."
  []
  (yarn :add {:bower "latest"}))
```

The `:cache-key` option is provided to avoid downloading node modules each time boot is restarted. This will cache the `node_modules` folder and include this folder in the fileset output.

```clojure
(boot/deftask bower
  "Install bower to node_modules."
  []
  (yarn :add   {:bower "latest"}
        :cache-key ::cache))
```

[1]: https://github.com/boot-clj/boot
