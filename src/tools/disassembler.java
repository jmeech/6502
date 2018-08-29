package tools;

import java.io.FileInputStream;
import java.io.IOException;

public class disassembler
{
  public static void main(String[] args) throws IOException
  {
  	System.out.printf("Disassembling %s...\n\n", args[0]);
  	
  	//get filename from args list and open
    FileInputStream fileIn = new FileInputStream(args[0]);
    byte[] header = new byte[16];
    
    fileIn.read(header);
    
    if(header[0] == 0x4e
    		&& header[1] == 0x45
    		&& header[2] == 0x53)
    {
    	System.out.printf("This ROM has an untouched header (%02x%02x%02x)\n", header[0], header[1], header[2]);
    }
    else
    {
    	System.out.printf("This ROM has a modified header (%02x%02x%02x)\n", header[0], header[1], header[2]);
    }
    
    int lim = 0;
    
    //get limit from args list
    try
    {
    	lim = Integer.parseInt(args[1]);
    }
    catch (NumberFormatException nfe)
    {
    	System.out.println("The first argument must be an integer.");
    	System.exit(1);;
    }
    
    // initialize
    int pc = 0;
    int x;
    int y;
    
    // Read bytes until EOF is encountered.
    do {

      // get instruction
      x = fileIn.read();
      
    	// print address and opcode
      System.out.printf("$%04X     %02x     ", pc, x);
      
      // print instruction
    	switch(x)
    	{
    		case 0x00 : System.out.printf("BRK\n"); ++pc; break;
    		case 0x01 : System.out.printf("ORA   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0x05 : System.out.printf("ORA   $%02x\n", fileIn.read()); pc += 2; break;
    		case 0x06 : System.out.printf("ASL   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x08 : System.out.printf("PHP\n"); ++pc; break;
    		case 0x09 : System.out.printf("ORA   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0x0A : System.out.printf("ASL   A\n"); ++pc; break;
    		case 0x0D : y = fileIn.read(); System.out.printf("ORA   $%02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x0E : y = fileIn.read(); System.out.printf("ASL   $%02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x10 : System.out.printf("BPL   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x11 : System.out.printf("ORA   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0x15 : System.out.printf("ORA   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x16 : System.out.printf("ASL   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x18 : System.out.printf("CLC\n"); ++pc; break; 
    		case 0x19 : y = fileIn.read(); System.out.printf("ORA   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0x1D : y = fileIn.read(); System.out.printf("ORA   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x1E : y = fileIn.read(); System.out.printf("ASL   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x20 : y = fileIn.read(); System.out.printf("JSR   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x21 : System.out.printf("AND   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0x24 : System.out.printf("BIT   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x25 : System.out.printf("AND   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x26 : System.out.printf("ROL   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x28 : System.out.printf("PLP\n"); ++pc; break;
    		case 0x29 : System.out.printf("AND   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0x2A : System.out.printf("ROL   A\n"); ++pc; break;
    		case 0x2C : y = fileIn.read(); System.out.printf("BIT   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x2D : y = fileIn.read(); System.out.printf("AND   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x2E : y = fileIn.read(); System.out.printf("ROL   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x30 : System.out.printf("BMI   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x31 : System.out.printf("AND   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0x35 : System.out.printf("AND   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x36 : System.out.printf("ROL   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x38 : System.out.printf("SEC\n"); ++pc; break;
    		case 0x39 : y = fileIn.read(); System.out.printf("AND   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0x3D : y = fileIn.read(); System.out.printf("AND   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x3E : y = fileIn.read(); System.out.printf("ROL   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x40 : System.out.printf("RTI\n"); ++pc; break;
    		case 0x41 : System.out.printf("EOR   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0x45 : System.out.printf("EOR   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x46 : System.out.printf("LSR   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x48 : System.out.printf("PHA\n"); ++pc; break;
    		case 0x49 : System.out.printf("EOR   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0x4A : System.out.printf("LSR   A\n"); ++pc; break;
    		case 0x4C : y = fileIn.read(); System.out.printf("JMP   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x4D : y = fileIn.read(); System.out.printf("EOR   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x4E : y = fileIn.read(); System.out.printf("LSR   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x50 : System.out.printf("BVC   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x51 : System.out.printf("EOR   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0x55 : System.out.printf("EOR   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x56 : System.out.printf("LSR   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x58 : System.out.printf("CLI\n"); ++pc; break;
    		case 0x59 : y = fileIn.read(); System.out.printf("EOR   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0x5D : y = fileIn.read(); System.out.printf("EOR   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x5E : y = fileIn.read(); System.out.printf("LSR   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x60 : System.out.printf("RTS\n"); ++pc; break;
    		case 0x61 : System.out.printf("ADC   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0x65 : System.out.printf("ADC   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x66 : System.out.printf("ROR   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x68 : System.out.printf("PLA\n"); ++pc; break;
    		case 0x69 : System.out.printf("ADC   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0x6A : System.out.printf("ROR   A\n"); ++pc; break;
    		case 0x6C : y = fileIn.read(); System.out.printf("JMP   (%02x%02x)\n", fileIn.read(), y); pc += 3; break;
    		case 0x6D : y = fileIn.read(); System.out.printf("ADC   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x6E : y = fileIn.read(); System.out.printf("ROR   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x70 : System.out.printf("BVS   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x71 : System.out.printf("ADC   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0x75 : System.out.printf("ADC   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x76 : System.out.printf("ROR   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x78 : System.out.printf("SEI\n"); ++pc; break;
    		case 0x79 : y = fileIn.read(); System.out.printf("ADC   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0x7D : y = fileIn.read(); System.out.printf("ADC   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x7E : y = fileIn.read(); System.out.printf("ROR   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0x81 : System.out.printf("STA   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0x84 : System.out.printf("STY   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x85 : System.out.printf("STA   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x86 : System.out.printf("STX   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x88 : System.out.printf("DEY\n"); ++pc; break;
    		case 0x8A : System.out.printf("TXA\n"); ++pc; break;
    		case 0x8C : y = fileIn.read(); System.out.printf("STY   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x8D : y = fileIn.read(); System.out.printf("STA   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x8E : y = fileIn.read(); System.out.printf("STX   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0x90 : System.out.printf("BCC   %02x\n", fileIn.read()); pc += 2; break;
    		case 0x91 : System.out.printf("STA   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0x94 : System.out.printf("STY   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x95 : System.out.printf("STA   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0x96 : System.out.printf("STX   %02x, Y\n", fileIn.read()); pc += 2; break;
    		case 0x98 : System.out.printf("TYA\n"); ++pc; break;
    		case 0x99 : y = fileIn.read(); System.out.printf("STA   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0x9A : System.out.printf("TXS\n"); ++pc; break;
    		case 0x9D : y = fileIn.read(); System.out.printf("STA   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0xA0 : System.out.printf("LDY   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0xA1 : System.out.printf("LDA   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0xA2 : System.out.printf("LDX   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0xA4 : System.out.printf("LDY   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xA5 : System.out.printf("LDA   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xA6 : System.out.printf("LDX   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xA8 : System.out.printf("TAY\n"); ++pc; break;
    		case 0xA9 : System.out.printf("LDA   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0xAA : System.out.printf("TAX\n"); ++pc; break;
    		case 0xAC : y = fileIn.read(); System.out.printf("LDY   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xAD : y = fileIn.read(); System.out.printf("LDA   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xAE : y = fileIn.read(); System.out.printf("LDX   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xB0 : System.out.printf("BCS   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xB1 : System.out.printf("LDA   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0xB4 : System.out.printf("LDY   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0xB5 : System.out.printf("LDA   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0xB6 : System.out.printf("LDX   %02x, Y\n", fileIn.read()); pc += 2; break;
    		case 0xB8 : System.out.printf("CLV\n"); ++pc; break;
    		case 0xB9 : y = fileIn.read(); System.out.printf("LDA   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0xBA : System.out.printf("TSX\n"); ++pc; break;
    		case 0xBC : y = fileIn.read(); System.out.printf("LDY   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0xBD : y = fileIn.read(); System.out.printf("LDA   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0xBE : y = fileIn.read(); System.out.printf("LDX   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0xC0 : System.out.printf("CPY   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0xC1 : System.out.printf("CMP   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0xC4 : System.out.printf("CPY   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xC5 : System.out.printf("CMP   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xC6 : System.out.printf("DEC   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xC8 : System.out.printf("INY\n"); ++pc; break;
    		case 0xC9 : System.out.printf("CMP   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0xCA : System.out.printf("DEX\n"); ++pc; break;
    		case 0xCC : y = fileIn.read(); System.out.printf("CPY   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xCD : y = fileIn.read(); System.out.printf("CMP   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xCE : y = fileIn.read(); System.out.printf("DEC   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xD0 : System.out.printf("BNE   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xD1 : System.out.printf("CMP   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0xD5 : System.out.printf("CMP   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0xD6 : System.out.printf("DEC   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0xD8 : System.out.printf("CLD\n"); ++pc; break;
    		case 0xD9 : y = fileIn.read(); System.out.printf("CMP   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0xDD : y = fileIn.read(); System.out.printf("CMP   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0xDE : y = fileIn.read(); System.out.printf("DEC   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0xE0 : System.out.printf("CPX   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0xE1 : System.out.printf("SBC   (%02x, X)\n", fileIn.read()); pc += 2; break;
    		case 0xE4 : System.out.printf("CPX   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xE5 : System.out.printf("SBC   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xE6 : System.out.printf("INC   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xE8 : System.out.printf("INX\n"); ++pc; break;
    		case 0xE9 : System.out.printf("SBC   #%02x\n", fileIn.read()); pc += 2; break;
    		case 0xEA : System.out.printf("NOP\n"); ++pc; break;
    		case 0xEC : y = fileIn.read(); System.out.printf("CPX   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xED : y = fileIn.read(); System.out.printf("SBC   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xEE : y = fileIn.read(); System.out.printf("INC   %02x%02x\n", fileIn.read(), y); pc += 3; break;
    		case 0xF0 : System.out.printf("BEQ   %02x\n", fileIn.read()); pc += 2; break;
    		case 0xF1 : System.out.printf("SBC   (%02x), Y\n", fileIn.read()); pc += 2; break;
    		case 0xF5 : System.out.printf("SBC   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0xF6 : System.out.printf("INC   %02x, X\n", fileIn.read()); pc += 2; break;
    		case 0xF8 : System.out.printf("SED\n"); ++pc; break;
    		case 0xF9 : y = fileIn.read(); System.out.printf("SBC   %02x%02x, Y\n", fileIn.read(), y); pc += 3; break;
    		case 0xFD : y = fileIn.read(); System.out.printf("SBC   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		case 0xFE : y = fileIn.read(); System.out.printf("INC   %02x%02x, X\n", fileIn.read(), y); pc += 3; break;
    		
    		default : System.out.printf("%02X\n", x); ++pc; break;
    	}
    	// continue until EOF or limit is reached
    } while ((fileIn.available() != 0) && (pc <= lim));
    
    fileIn.close();    
    System.exit(0);
  }
}