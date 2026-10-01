# Algo-Traj Android Node

Android/Kotlin baseline for the Algo-Traj signal-to-trajectory engine and local
ledger. The application package is `com.austinenterprisellc.algotraj`.

## Build

Open this project in Android Studio with Android SDK 35 installed, or run:

```sh
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```

The engine accepts timestamped signals, validates confidence values, and
generates an in-memory trajectory per entity. Ledger records use Room; remote
ledger synchronization is not configured yet.

## VPN service

The manifest registers `AlgoTrajVpnService`, but it does not establish a VPN
interface or redirect device traffic. The provided packet loop routed all IPv4
traffic to a tunnel without forwarding packets, which would interrupt network
connectivity. A traffic-capturing implementation requires a complete, tested
packet-forwarding design before it is safe to enable.
