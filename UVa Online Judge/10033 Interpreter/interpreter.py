# hk 3 / 20

from typing import List, Tuple

class Machine:
    NUM_REGISTERS = 10
    RAM_SIZE = 1000
    REGISTER_SIZE = 1000

    def f0(self, d, s):  self.pc = self.regs[d] -1 if self.regs[s] != 0 else self.pc
    def f1(self, *_):    self.halted = True    
    def f2(self, d, n):  self.regs[d] = n   
    def f3(self, d, n):  self.regs[d] = (self.regs[d] + n) % Machine.REGISTER_SIZE
    def f4(self, d, n):  self.regs[d] = (self.regs[d] * n) % Machine.REGISTER_SIZE
    def f5(self, d, s):  self.regs[d] = self.regs[s]
    def f6(self, d, s):  self.regs[d] = (self.regs[d] + self.regs[s]) % Machine.REGISTER_SIZE
    def f7(self, d, s):  self.regs[d] = (self.regs[d] * self.regs[s]) % Machine.REGISTER_SIZE
    def f8(self, d, a):  self.regs[d] = self.ram[self.regs[a]]
    def f9(self, s, a):  self.ram[self.regs[a]] = self.regs[s]   

    OP_TABLE = [f0, f1, f2, f3, f4, f5, f6, f7, f8, f9] 


    def __init__(self):
        self.reset()


    def reset(self) -> None:
        self.regs = [0] * Machine.NUM_REGISTERS
        self.ram = [0] * Machine.RAM_SIZE
        self.pc = 0
        self.num_executed = 0
        self.halted = False


    def load_program(self, program: List[int]) -> None:
        self.ram[0 : len(program)] = program


    @staticmethod
    def _parse_op(op: int) -> Tuple[int, int, int]:
        return (op // 100, (op % 100) // 10, op % 10)   


    def step(self) -> None:
        assert not self.halted, "Can't step if halted!"
        self.pc %= len(self.ram)    # rollover if necessary?
        op, arg1, arg2 = Machine._parse_op(self.ram[self.pc])    
        Machine.OP_TABLE[op](self, arg1, arg2) 
        self.pc += 1
        self.num_executed += 1


    def run(self) -> None:
        while not self.halted:
            self.step()

    def get_num_executed(self) -> int:
        return self.num_executed        




def read_program() -> List[int]:
    ops = []
    
    try:
        while True:
            ops.append(int(input()))
    except (EOFError, ValueError):
        return ops



if __name__ == '__main__':    
    num_cases = int(input())
    input()

    machine = Machine() 
    
    for i in range(num_cases):
        machine.reset()
        machine.load_program(read_program())
        machine.run()
        print(machine.get_num_executed())
        
        if i < num_cases -1:
            print()

