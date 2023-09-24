# Virtual threads GlassFish thread pool 

An implementation of a GlassFish thread pool that schedules tasks on virtual threads instead of platform threads.

## Build

```
mvn install
```

(requires Java 21+ to build)

## Usage

1. After building, copy `target/grizzly-virtual-threads-pool.jar` into an existing GlassFish installation, either to `glassfish/modules`, or to `glassfish/domains/domain1/lib`
2. Run GlassFish 7 on Java 21
3. Edit http-thread-pool thread pool and set the `Class Name` to: `org.glassfish.grizzlyintegration.virtualthreads.VirtualThreadsExecutorService`

![image](https://github.com/OmniFish-EE/glassfish-grizzly-virtual-threads-pool/assets/2195988/d5d4bbca-4d09-44e8-85a4-6a736edf9c36)

### Notes

When the above steps are performed with an older Java version than 21, all will work, but the thread pool will fall back to the default mechanism, which uses platform threads.
