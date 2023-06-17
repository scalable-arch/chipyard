package chipyard.fpga.arty35t

import chisel3._

import freechips.rocketchip.diplomacy.{LazyModule}
import freechips.rocketchip.prci.{ClockBundle, ClockBundleParameters}
import org.chipsalliance.cde.config.{Parameters}

import sifive.fpgashells.shell.xilinx.artyshell.{Arty35TShell}

import chipyard.harness.{HasHarnessInstantiators}
import chipyard.iobinders.{HasIOBinders}

class Arty35THarness(override implicit val p: Parameters) extends Arty35TShell with HasHarnessInstantiators {
  // Convert harness resets from Bool to Reset type.
  val hReset = Wire(Reset())
  hReset := ~ck_rst

  val dReset = Wire(AsyncReset())
  dReset := reset_core.asAsyncReset

  def success = {require(false, "Success not supported"); false.B }

  def referenceClockFreqMHz = 32.0
  def referenceClock = clock_32MHz
  def referenceReset = hReset

  instantiateChipTops()
}