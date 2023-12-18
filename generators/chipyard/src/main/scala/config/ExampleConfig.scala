package chipyard

import org.chipsalliance.cde.config.{Config}
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.subsystem.{MBUS, SBUS}
import testchipip.{OBUS}

// 
class ExampleConfig extends Config (
  new chipyard.config.AbstractConfig
)

/**
  * An example Chip Config demonstrating how to set up a basic chip in Chipyard
  * 
  * @see Chipyard Tutorial: [Getting Started with Chipyard](https://)
  */
class ExampleChipConfig extends Config(
  // ==================================
  //   Set up TestHarness
  // ==================================
  // The HarnessBinders control generation of hardware in the TestHarness
  new chipyard.harness.WithUARTAdapter ++                          // add UART adapter to display UART on stdout, if uart is present
  new chipyard.harness.WithBlackBoxSimMem ++                       // add SimDRAM DRAM model for axi4 backing memory, if axi4 mem is enabled
  new chipyard.harness.WithSimTSIOverSerialTL ++                   // add external serial-adapter and RAM
  new chipyard.harness.WithSimJTAGDebug ++                         // add SimJTAG if JTAG for debug exposed
  new chipyard.harness.WithSimDMI ++                               // add SimJTAG if DMI exposed
  new chipyard.harness.WithGPIOTiedOff ++                          // tie-off chiptop GPIOs, if GPIOs are present
  new chipyard.harness.WithSimSPIFlashModel ++                     // add simulated SPI flash memory, if SPI is enabled
  new chipyard.harness.WithSimAXIMMIO ++                           // add SimAXIMem for axi4 mmio port, if enabled
  new chipyard.harness.WithTieOffInterrupts ++                     // tie-off interrupt ports, if present
  new chipyard.harness.WithTieOffL2FBusAXI ++                      // tie-off external AXI4 master, if present
  new chipyard.harness.WithCustomBootPinPlusArg ++                 // drive custom-boot pin with a plusarg, if custom-boot-pin is present
  new chipyard.harness.WithSimUARTToUARTTSI ++                     // connect a SimUART to the UART-TSI port
  new chipyard.harness.WithClockFromHarness ++                     // all Clock I/O in ChipTop should be driven by harnessClockInstantiator
  new chipyard.harness.WithResetFromHarness ++                     // reset controlled by harness
  new chipyard.harness.WithAbsoluteFreqHarnessClockInstantiator ++ // generate clocks in harness with unsynthesizable ClockSourceAtFreqMHz

  // ==================================
  //   Set up I/O harness
  // ==================================
  // The IOBinders instantiate ChipTop IOs to match desired digital IOs
  // IOCells are generated for "Chip-like" IOs, while simulation-only IOs are directly punched through
  new chipyard.iobinders.WithSerialTLIOCells ++
  new chipyard.iobinders.WithDebugIOCells ++
  new chipyard.iobinders.WithUARTIOCells ++
  new chipyard.iobinders.WithGPIOCells ++
  new chipyard.iobinders.WithSPIFlashIOCells ++
  new chipyard.iobinders.WithExtInterruptIOCells ++
  new chipyard.iobinders.WithCustomBootPin ++
  // The "punchthrough" IOBInders below don't generate IOCells, as these interfaces shouldn't really be mapped to ASIC IO
  // Instead, they directly pass through the DigitalTop ports to ports in the ChipTop
  new chipyard.iobinders.WithI2CPunchthrough ++
  new chipyard.iobinders.WithSPIIOPunchthrough ++
  new chipyard.iobinders.WithAXI4MemPunchthrough ++
  new chipyard.iobinders.WithAXI4MMIOPunchthrough ++
  new chipyard.iobinders.WithTLMemPunchthrough ++
  new chipyard.iobinders.WithL2FBusAXI4Punchthrough ++
  new chipyard.iobinders.WithBlockDeviceIOPunchthrough ++
  new chipyard.iobinders.WithNICIOPunchthrough ++
  new chipyard.iobinders.WithTraceIOPunchthrough ++
  new chipyard.iobinders.WithUARTTSIPunchthrough ++
  new chipyard.iobinders.WithNMITiedOff ++

  // ==================================
  //   Set up Memory Devices
  // ==================================

  // External memory section
  new testchipip.WithSerialTLClientIdBits(1) ++                     // support up to 1 << 4 simultaneous requests from serialTL port
  new testchipip.WithSerialTLWidth(1) ++                           // fatten the serialTL interface to improve testing performance
  new testchipip.WithDefaultSerialTL ++                             // use serialized tilelink port to external serialadapter/harnessRAM
  new testchipip.WithSerialTLClockDirection(provideClockFreqMHz = Some(75)) ++ // bringup board drives the clock for the serial-tl receiver on the chip, use 75MHz clock

  // new testchipip.WithNoSerialTL ++

  new freechips.rocketchip.subsystem.WithNoMemPort ++             // remove AXI DRAM memory port

  new testchipip.WithMbusScratchpad(base = 0x08000000, size = 128 * 1024) ++       // use rocket l1 DCache scratchpad as base phys mem

  // Peripheral section
  // new chipyard.config.WithUART(address = 0x10022000, baudrate = 115200) ++
  // new chipyard.config.WithUART(address = 0x10021000, baudrate = 115200) ++
  new chipyard.config.WithUART(address = 0x10020000, baudrate = 115200) ++

  // new chipyard.config.WithGPIO(address = 0x10011000, width = 24) ++
  // new chipyard.config.WithGPIO(address = 0x10010000, width = 3) ++

  // Core section
  new chipyard.config.WithBootROM ++                                // use default bootrom
  new testchipip.WithCustomBootPin ++                               // add a custom-boot-pin to support pin-driven boot address
  new testchipip.WithBootAddrReg ++                                 // add a boot-addr-reg for configurable boot address                            // use default bootrom

  // ==================================
  //   Set up tiles
  // ==================================
  // Debug settings
  new chipyard.config.WithDebugModuleAbstractDataWords(8) ++        // increase debug module data capacity
  new chipyard.config.WithJTAGDTMKey(idcodeVersion = 2, partNum = 0x000, manufId = 0x489, debugIdleCycles = 5) ++
  new freechips.rocketchip.subsystem.WithNBreakpoints(2) ++
  new freechips.rocketchip.subsystem.WithJtagDTM ++                 // set the debug module to expose a JTAG port

  // Cache settings
  // new freechips.rocketchip.subsystem.WithL1ICacheSets(64) ++
  // new freechips.rocketchip.subsystem.WithL1ICacheWays(2) ++
  // new freechips.rocketchip.subsystem.WithL1DCacheSets(64) ++
  // new freechips.rocketchip.subsystem.WithL1DCacheWays(2) ++
  new chipyard.config.WithL2TLBs(0) ++
  // new freechips.rocketchip.subsystem.WithInclusiveCache ++          // use Sifive L2 cache

  // Memory settings
  new chipyard.config.WithNPMPs(0) ++
  new freechips.rocketchip.subsystem.WithNMemoryChannels(2) ++      // Default 2 memory channels
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++              // no top-level MMIO master port (overrides default set in rocketchip)
  new freechips.rocketchip.subsystem.WithNoSlavePort ++             // no top-level MMIO slave port (overrides default set in rocketchip)
  new freechips.rocketchip.subsystem.WithCoherentBusTopology ++     // hierarchical buses including sbus/mbus/pbus/fbus/cbus/l2

  // Core settings
  new freechips.rocketchip.subsystem.WithNExtTopInterrupts(0) ++    // no external interrupts
  new freechips.rocketchip.subsystem.WithNSmallCores(1) ++

  // ==================================
  //   Set up reset and clocking
  // ==================================
  new freechips.rocketchip.subsystem.WithDontDriveBusClocksFromSBus ++ // leave the bus clocks undriven by sbus
  new freechips.rocketchip.subsystem.WithClockGateModel ++          // add default EICG_wrapper clock gate model
  new chipyard.clocking.WithPassthroughClockGenerator ++
  new chipyard.clocking.WithClockGroupsCombinedByName(("uncore", Seq("sbus", "mbus", "pbus", "fbus", "cbus", "implicit"), Seq("tile"))) ++
  new chipyard.config.WithPeripheryBusFrequency(32.0) ++           // Default 500 MHz pbus
  new chipyard.config.WithSystemBusFrequency(32.0) ++
  new chipyard.config.WithMemoryBusFrequency(32.0) ++              // Default 500 MHz mbus
  new chipyard.config.WithNoSubsystemDrivenClocks ++                // drive the subsystem diplomatic clocks from ChipTop instead of using implicit clocks
  new chipyard.config.WithInheritBusFrequencyAssignments ++         // Unspecified clocks within a bus will receive the bus frequency if set

  // ==================================
  //   Base Settings
  // ==================================
  new freechips.rocketchip.subsystem.WithDTS("ucb-bar, chipyard", Nil) ++ // custom device name for DTS
  new freechips.rocketchip.system.BaseConfig                        // "base" rocketchip system
)
