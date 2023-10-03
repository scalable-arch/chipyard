package chipyard

import org.chipsalliance.cde.config.{Config}

class V64D64Config extends Config(
  new chipyard.harness.WithCospike ++
  new chipyard.config.WithTraceIO ++
  new vector.rocket.WithRocketVectorUnit(64, 64) ++
  new freechips.rocketchip.subsystem.WithRocketCease(false) ++
  new freechips.rocketchip.subsystem.WithRocketDebugROB ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new chipyard.config.AbstractConfig)

class V128D64Config extends Config(
  new chipyard.harness.WithCospike ++
  new chipyard.config.WithTraceIO ++
  new vector.rocket.WithRocketVectorUnit(128, 64) ++
  new freechips.rocketchip.subsystem.WithRocketCease(false) ++
  new freechips.rocketchip.subsystem.WithRocketDebugROB ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new chipyard.config.AbstractConfig)

class V256D64Config extends Config(
  new chipyard.harness.WithCospike ++
  new chipyard.config.WithTraceIO ++
  new vector.rocket.WithRocketVectorUnit(256, 64) ++
  new freechips.rocketchip.subsystem.WithRocketCease(false) ++
  new freechips.rocketchip.subsystem.WithRocketDebugROB ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new chipyard.config.AbstractConfig)

class V128D128Config extends Config(
  new chipyard.harness.WithCospike ++
  new chipyard.config.WithTraceIO ++
  new vector.rocket.WithRocketVectorUnit(128, 128) ++
  new chipyard.config.WithSystemBusWidth(128) ++
  new freechips.rocketchip.subsystem.WithRocketCease(false) ++
  new freechips.rocketchip.subsystem.WithRocketDebugROB ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new chipyard.config.AbstractConfig)

class V256D128Config extends Config(
  new chipyard.harness.WithCospike ++
  new chipyard.config.WithTraceIO ++
  new vector.rocket.WithRocketVectorUnit(256, 128) ++
  new chipyard.config.WithSystemBusWidth(128) ++
  new freechips.rocketchip.subsystem.WithRocketCease(false) ++
  new freechips.rocketchip.subsystem.WithRocketDebugROB ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new chipyard.config.AbstractConfig)

class V512D128Config extends Config(
  new chipyard.harness.WithCospike ++
  new chipyard.config.WithTraceIO ++
  new vector.rocket.WithRocketVectorUnit(512, 128) ++
  new chipyard.config.WithSystemBusWidth(128) ++
  new freechips.rocketchip.subsystem.WithRocketCease(false) ++
  new freechips.rocketchip.subsystem.WithRocketDebugROB ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new chipyard.config.AbstractConfig)

