# immutant-meetup

This repository will be the base for the talk at the next [Copenhangen Clojure Meetup][].

At the time of writing Immutant version 1.1.3 is the latest stable version.

[Copenhangen Clojure Meetup]: http://www.meetup.com/Copenhagen-Clojure-Meetup/events/190043432/


# Steps


## Step 1

This repo was created by:

    lein new immutant-meetup

Include compojure in the project.clj files dependencies:

    [compojure "1.1.8"]

Compojure well be used later in this tutorial.


## Step 2 - Install Immutant

Add the following to the ´~/.lein/profiles.clj´ file:

    {:user {:plugins [[lein-immutant "1.2.1"]]}}

Then install using `lein`:

    lein immutant install

This will install the latest stable version.

More information about [installing][1] (including alternative ways) on
the official website.


## Step 3 - Spin it up

Start Immutant using:

    lein immutant run

After seeing `JBoss AS 7.2.x.slim.incremental.16 "Janus" started in`
in the console Immutant has starter but the application hasn't been
deployd to the server yet.

This is done by doing (from within the project folder):

    lein immutant deploy

On the console Immutant will tell us:

    Starting deployment of "immutant-meetup.clj"

and after a short while:

    Deployed "immutant-meetup.clj"

The order in which `run`/`deploy` is executed is irreleveant.

To stop a running Immutant instance press `Ctrl - c`.
When Immutant is restarted all applications are (re-)deployed.


## Step 4 - nREPL

By default applications are deployed using a :dev profile which by
default among other things display stacktraces in the console,
auto reloads source files when they are saved and starts a nREPL.

For the rest of this walkthrough auto reload have been disabled.
Give web/start the options `:auto-reload? true` and re-deploy.
Code will be evaluated (and re-evaluated) using the REPL.

The REPL will start on a random port number and can be found by
looking in the console in the output from the deployment, ie.:

    nREPL bound to 127.0.0.1:33532

To test it connect to it using:

    lein repl :connect localhost:33532

This is pretty neat compared to normal JBoss development, because it
is easy to change and re-evaluate code with out redeploy.


## Step 5 - Web

Immutant is now up and running and available via a nREPL but let's
give it something to show off. For this the Web component is an
excellent place to start.

Define a ring handler. This example uses `immutant-meetup.core` as
placeholder:

    (ns immutant-meetup.core)
    (use 'compojure.core)
    (require '[compojure.route :as route])
    
    (defroutes app
      (GET "/" [] "<h1>Hello World</h1>")
      (route/not-found "<h1>Page not found</h1>"))

Immutant supports using `:ring` (from lein-ring) in `project.clj`, to
setup a web handler but the `immutant.init` namespace is better suited
to initialize all round applications specific things.

    (ns immutant.init)
    (use 'immutant-meetup.core)
    (require '[immutant.web :as web])
    
    (web/start app)

Now we can access the application on http://localhost:8080/immutant-meetup/


## Step 6 - Messaging

Using messaging requires the `immutant.messaging` namespace.

    (ns immutant.init)
    (require '[immutant.messaging :as msg])

Start it using:

    (msg/start "topic/println")

`start` can take a number of different [options][2].

Attach listener using:

    (msg/listen "topic/println" #(println %))

Now send somthing to the "topic":

    (msg/publish "topic/println" "Hello everybody!")

To attach another listener to the topic:

    (msg/listen "topic/println"  #(println (str "2: " %)))

Starting of the topic and attaching a listener have been added to the
`immutant.init` namespace.


## Step 7 - Messaging taken to the next level

Now we want to combine the web and messaging components.

    (ns immutant-meetup.core)
    (require '[immutant.messaging :as msg])

Evaluate this function:

    (defn publish-my-topic
      [text]
      (msg/publish "topic/println" text)
      (str "<h1>Topic</h1><br>Published: " text))

And re-evaluate `app` to include a new route:

    (GET "/topic/:text" [text] (publish-my-topic text))

Now access the URL: http://localhost:8080/immutant-meetup/topic/sometext
and watch the console for the text `sometext`.


## Step 8 - Scheduling

Lets start by adding a atom to hold the number of updates done by our scheduled job

    (ns immutant-meetup.core)
    (def updates (atom 0))

Then we modify our route for the application root, remember to re-evaluate `app` to update route:

    (GET "/" [] (str "<h1>Hello World</h1><br>Updated: " @updates " times"))

Now lets setup a scheduled job, to actually count up our atom.
Using scheduling requires the `immutant.jobs` namespace.

    (ns immutant.init)
    (use 'immutant-meetup.core) ; need to reload to see "updates" atom
    (require '[immutant.jobs :as jobs])

To schedule a job:

    (jobs/schedule "update-counter"
                   #(swap! updates inc)
                   "*/10 * * * * ?")

Now access the URL: http://localhost:8080/immutant-meetup/ and watch the update change
every 5 minute on reloads.


## Step 9 - Deployment with archives

First create an Immutant archive of the application

    lein immutant archive

Then deploy it on the Immutant server

    cp immutant-meetup.ima ~/.immutant/current/jboss/standalone/deployments/
    touch ~/.immutant/current/jboss/standalone/deployments/immutant-meetup.ima.dodeploy


## Step 10 - Deployment descriptors

For better control of the deployment, use deployment descriptors. Create a deployment descriptor named my-descriptor.clj containing a map with options.

    {:root "~/immutant-meetup.ima"
     :context-path "/demo"
     :nrepl-port 12345
     :custom-key "test"}

To deploy an archive through a deployment descriptor

    cp my-descriptor.clj ~/.immutant/current/jboss/standalone/deployments/
    touch ~/.immutant/current/jboss/standalone/deployments/my-descriptor.clj.dodeploy


[1]: http://immutant.org/tutorials-1x/installation/index.html
[2]: http://immutant.org/documentation/current/messaging.html#sec-2-3

## License

Copyright © 2014

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
