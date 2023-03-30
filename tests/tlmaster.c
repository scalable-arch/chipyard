#include "mmio.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <riscv-pk/encoding.h>
#include "tlmaster.h"


#define N_TRIALS 10


int main(void) {
  printf("Test started\n");

  for (int i = 0; i < N_TRIALS; i++) {
    tlmaster_send_write_req(i * 8);
  }

  for (int i = 0; i < N_TRIALS; i++) {
    tlmaster_send_read_req(i * 8);
  }

  printf("Test ended\n");
  return 0;
}
