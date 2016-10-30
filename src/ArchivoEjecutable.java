
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchivoEjecutable 
{

    private String nombre;
    private DataOutputStream bwe;
    private byte memoria[];
    private int proxLibre;

    public ArchivoEjecutable(String nombre) throws FileNotFoundException 
    {
        this.proxLibre = 0;
        this.memoria = new byte[Constantes.MAX_TAM_ARCH];
        
        memoria[0] = 0x4D; // 'M'  (Magic number)
        memoria[1] = 0x5A; // 'Z' 

        memoria[2] = 0x60; // Bytes on last block
        memoria[3] = 0x01; // (1 bl. = 512 bytes)

        memoria[4] = 0x01; // Number of blocks 
        memoria[5] = 0x00; // in the EXE file 

        memoria[6] = 0x00; // Number of re- 
        memoria[7] = 0x00; // location entries 

        memoria[8] = 0x04; // Size of header 
        memoria[9] = 0x00; // (x 16 bytes) 

        memoria[10] = 0x00; // Minimum extra 
        memoria[11] = 0x00; // paragraphs needed 

        memoria[12] = (byte) 0xFF; // Maximum extra 
        memoria[13] = (byte) 0xFF; // paragraphs needed 

        memoria[14] = 0x00; // Initial (relative)
        memoria[15] = 0x00; // SS value 

        memoria[16] = 0x60; // Initial SP value 
        memoria[17] = 0x01; 

        memoria[18] = 0x00; // Checksum 
        memoria[19] = 0x00; 

        memoria[20] = 0x00; // Initial IP value 
        memoria[21] = 0x00; 

        memoria[22] = 0x00; // Initial (relative)
        memoria[23] = 0x00; // CS value 

        memoria[24] = 0x40; // Offset of the 1st 
        memoria[25] = 0x00; // relocation item 

        memoria[26] = 0x00; // Overlay number. 
        memoria[27] = 0x00; // (0 = main program)

        memoria[28] = 0x00; // Reserved word 
        memoria[29] = 0x00; 

        memoria[30] = 0x00; // Reserved word 
        memoria[31] = 0x00; 

        memoria[32] = 0x00; // Reserved word 
        memoria[33] = 0x00; 

        memoria[34] = 0x00; // Reserved word 
        memoria[35] = 0x00; 

        memoria[36] = 0x00; // OEM identifier 
        memoria[37] = 0x00; 

        memoria[38] = 0x00; // OEM information 
        memoria[39] = 0x00; 

        memoria[40] = 0x00; // Reserved word 
        memoria[41] = 0x00;
        memoria[42] = 0x00; // Reserved word 
        memoria[43] = 0x00; 

        memoria[44] = 0x00; // Reserved word 
        memoria[45] = 0x00; 

        memoria[46] = 0x00; // Reserved word 
        memoria[47] = 0x00; 

        memoria[48] = 0x00; // Reserved word 
        memoria[49] = 0x00; 

        memoria[50] = 0x00; // Reserved word 
        memoria[51] = 0x00; 

        memoria[52] = 0x00; // Reserved word 
        memoria[53] = 0x00; 

        memoria[54] = 0x00; // Reserved word 
        memoria[55] = 0x00; 

        memoria[56] = 0x00; // Reserved word 
        memoria[57] = 0x00; 

        memoria[58] = 0x00; // Reserved word 
        memoria[59] = 0x00; 

        memoria[60] = (byte) 0xA0; // Start of the COFF 
        memoria[61] = 0x00; // header 
        memoria[62] = 0x00; 
        memoria[63] = 0x00; 

        memoria[64] = 0x0E; // PUSH CS 

        memoria[65] = 0x1F; // POP DS 

        memoria[66] = (byte) 0xBA; // MOV DX,000E 
        memoria[67] = 0x0E; 
        memoria[68] = 0x00; 

        memoria[69] = (byte) 0xB4; // MOV AH,09 
        memoria[70] = 0x09; 

        memoria[71] = (byte) 0xCD; // INT 21 
        memoria[72] = 0x21; 

        memoria[73] = (byte) 0xB8; // MOV AX,4C01 
        memoria[74] = 0x01; 
        memoria[75] = 0x4C; 

        memoria[76] = (byte) 0xCD; // INT 21 
        memoria[77] = 0x21; 

        memoria[78] = 0x54; // 'T' 
        memoria[79] = 0x68; // 'h' 
        memoria[80] = 0x69; // 'i' 
        memoria[81] = 0x73; // 's' 
        memoria[82] = 0x20; // ' ' 
        memoria[83] = 0x70; // 'p' 
        memoria[84] = 0x72; // 'r' 
        memoria[85] = 0x6F; // 'o' 
        memoria[86] = 0x67; // 'g' 
        memoria[87] = 0x72; // 'r' 
        memoria[88] = 0x61; // 'a'

        memoria[89] = 0x6D; // 'm' 
        memoria[90] = 0x20; // ' ' 
        memoria[91] = 0x69; // 'i' 
        memoria[92] = 0x73; // 's' 
        memoria[93] = 0x20; // ' ' 
        memoria[94] = 0x61; // 'a' 
        memoria[95] = 0x20; // ' ' 
        memoria[96] = 0x57; // 'W' 
        memoria[97] = 0x69; // 'i' 
        memoria[98] = 0x6E; // 'n' 
        memoria[99] = 0x33; // '3' 
        memoria[100] = 0x32; // '2' 
        memoria[101] = 0x20; // ' ' 
        memoria[102] = 0x63; // 'c' 
        memoria[103] = 0x6F; // 'o' 
        memoria[104] = 0x6E; // 'n' 
        memoria[105] = 0x73; // 's' 
        memoria[106] = 0x6F; // 'o' 
        memoria[107] = 0x6C; // 'l' 
        memoria[108] = 0x65; // 'e' 
        memoria[109] = 0x20; // ' ' 
        memoria[110] = 0x61; // 'a' 
        memoria[111] = 0x70; // 'p' 
        memoria[112] = 0x70; // 'p' 
        memoria[113] = 0x6C; // 'l' 
        memoria[114] = 0x69; // 'i' 
        memoria[115] = 0x63; // 'c' 
        memoria[116] = 0x61; // 'a' 
        memoria[117] = 0x74; // 't' 
        memoria[118] = 0x69; // 'i' 
        memoria[119] = 0x6F; // 'o' 
        memoria[120] = 0x6E; // 'n' 
        memoria[121] = 0x2E; // '.' 
        memoria[122] = 0x20; // ' ' 
        memoria[123] = 0x49; // 'I' 
        memoria[124] = 0x74; // 't' 
        memoria[125] = 0x20; // ' ' 
        memoria[126] = 0x63; // 'c' 
        memoria[127] = 0x61; // 'a' 
        memoria[128] = 0x6E; // 'n' 
        memoria[129] = 0x6E; // 'n' 
        memoria[130] = 0x6F; // 'o' 
        memoria[131] = 0x74; // 't' 
        memoria[132] = 0x20; // ' ' 
        memoria[133] = 0x62; // 'b' 
        memoria[134] = 0x65; // 'e' 
        memoria[135] = 0x20; // ' ' 
        memoria[136] = 0x72; // 'r' 
        memoria[137] = 0x75; // 'u' 
        memoria[138] = 0x6E; // 'n' 
        memoria[139] = 0x20; // ' ' 
        memoria[140] = 0x75; // 'u' 
        memoria[141] = 0x6E; // 'n' 
        memoria[142] = 0x64; // 'd' 
        memoria[143] = 0x65; // 'e' 
        memoria[144] = 0x72; // 'r' 
        memoria[145] = 0x20; // ' ' 
        memoria[146] = 0x4D; // 'M' 
        memoria[147] = 0x53; // 'S' 
        memoria[148] = 0x2D; // '-' 
        memoria[149] = 0x44; // 'D' 
        memoria[150] = 0x4F; // 'O' 
        memoria[151] = 0x53; // 'S' 
        memoria[152] = 0x2E; // '.' 
        memoria[153] = 0x0D; // Carriage return 
        memoria[154] = 0x0A; // Line feed
        memoria[155] = 0x24; // String end ('$') 
        memoria[156] = 0x00; 
        memoria[157] = 0x00; 
        memoria[158] = 0x00; 
        memoria[159] = 0x00; 

        /* COFF HEADER - 8 Standard fields */ 

        memoria[160] = 0x50; // 'P' 
        memoria[161] = 0x45; // 'E' 
        memoria[162] = 0x00; // '\0' 
        memoria[163] = 0x00; // '\0' 

        memoria[164] = 0x4C; // Machine: 
        memoria[165] = 0x01; // >= Intel 386 

        memoria[166] = 0x01; // Number of 
        memoria[167] = 0x00; // sections 

        memoria[168] = 0x00; // Time/Date stamp 
        memoria[169] = 0x00; 
        memoria[170] = 0x53; 
        memoria[171] = 0x4C; 

        memoria[172] = 0x00; // Pointer to symbol 
        memoria[173] = 0x00; // table 
        memoria[174] = 0x00; // (deprecated) 
        memoria[175] = 0x00; 

        memoria[176] = 0x00; // Number of symbols 
        memoria[177] = 0x00; // (deprecated) 
        memoria[178] = 0x00; 
        memoria[179] = 0x00; 

        memoria[180] = (byte) 0xE0; // Size of optional 
        memoria[181] = 0x00; // header 

        memoria[182] = 0x02; // Characteristics: 
        memoria[183] = 0x01; // 32BIT_MACHINE EXE 

        /* OPTIONAL HEADER - 8 Standard fields */ 
        /*  (For image files, it is required)  */ 

        memoria[184] = 0x0B; // Magic number 
        memoria[185] = 0x01; // (010B = PE32) 

        memoria[186] = 0x01;// Maj.Linker Version 

        memoria[187] = 0x00;// Min.Linker Version 

        memoria[188] = 0x00; // Size of code 
        memoria[189] = 0x06; // (text) section 
        memoria[190] = 0x00; 
        memoria[191] = 0x00; 

        memoria[192] = 0x00; // Size of 
        memoria[193] = 0x00; // initialized data 
        memoria[194] = 0x00; // section 
        memoria[195] = 0x00; 

        memoria[196] = 0x00; // Size of 
        memoria[197] = 0x00; // uninitialized 
        memoria[198] = 0x00; // data section 
        memoria[199] = 0x00; 


        memoria[200] = 0x00; // Starting address 
        memoria[201] = 0x15; // relative to the 
        memoria[202] = 0x00; // image base 
        memoria[203] = 0x00; 

        memoria[204] = 0x00; // Base of code 
        memoria[205] = 0x10; 
        memoria[206] = 0x00; 
        memoria[207] = 0x00; 

        /* OPT.HEADER - 1 PE32 specific field */ 

        memoria[208] = 0x00; // Base of data 
        memoria[209] = 0x20; 
        memoria[210] = 0x00; 
        memoria[211] = 0x00; 

        /* OPT.HEADER - 21 Win-Specific Fields */ 

        memoria[212] = 0x00; // Image base 
        memoria[213] = 0x00; // (Preferred 
        memoria[214] = 0x40; // address of image 
        memoria[215] = 0x00; // when loaded) 

        memoria[216] = 0x00; // Section alignment 
        memoria[217] = 0x10; 
        memoria[218] = 0x00; 
        memoria[219] = 0x00; 

        memoria[220] = 0x00; // File alignment 
        memoria[221] = 0x02; // (Default is 512) 
        memoria[222] = 0x00; 
        memoria[223] = 0x00; 

        memoria[224] = 0x04; // Major OS version 
        memoria[225] = 0x00; 

        memoria[226] = 0x00; // Minor OS version 
        memoria[227] = 0x00; 

        memoria[228] = 0x00;// Maj. image version 
        memoria[229] = 0x00; 

        memoria[230] = 0x00;// Min. image version 
        memoria[231] = 0x00; 

        memoria[232] = 0x04;// Maj.subsystem ver. 
        memoria[233] = 0x00; 

        memoria[234] = 0x00;// Min.subsystem ver. 
        memoria[235] = 0x00; 

        memoria[236] = 0x00; // Win32 version 
        memoria[237] = 0x00; // (Reserved, must 
        memoria[238] = 0x00; // be zero) 
        memoria[239] = 0x00; 

        memoria[240] = 0x00;// Size of image 
        memoria[241] = 0x20;// (It must be a  
        memoria[242] = 0x00;// multiple of the  
        memoria[243] = 0x00;// section alignment) 

        memoria[244] = 0x00; // Size of headers 
        memoria[245] = 0x02; // (rounded up to a 
        memoria[246] = 0x00; // multiple of the 
        memoria[247] = 0x00; // file alignment)
        memoria[248] = 0x00; // Checksum 
        memoria[249] = 0x00; 
        memoria[250] = 0x00; 
        memoria[251] = 0x00; 

        memoria[252] = 0x03; // Windows subsystem 
        memoria[253] = 0x00; // (03 = console) 

        memoria[254] = 0x00; // DLL charac-
        memoria[255] = 0x00; // teristics 

        memoria[256] = 0x00; // Size of stack 
        memoria[257] = 0x00; // reserve 
        memoria[258] = 0x10; 
        memoria[259] = 0x00; 

        memoria[260] = 0x00; // Size of stack 
        memoria[261] = 0x10; // commit 
        memoria[262] = 0x00; 
        memoria[263] = 0x00; 

        memoria[264] = 0x00; // Size of heap 
        memoria[265] = 0x00; // reserve 
        memoria[266] = 0x10; 
        memoria[267] = 0x00; 

        memoria[268] = 0x00; // Size of heap 
        memoria[269] = 0x10; // commit 
        memoria[270] = 0x00; 
        memoria[271] = 0x00; 

        memoria[272] = 0x00; // Loader flags 
        memoria[273] = 0x00; // (Reserved, must 
        memoria[274] = 0x00; // be zero) 
        memoria[275] = 0x00; 

        memoria[276] = 0x10; // Number of 
        memoria[277] = 0x00; // relative virtual 
        memoria[278] = 0x00; // addresses (RVAs) 
        memoria[279] = 0x00; 

        /* OPT. HEADER - 16 Data Directories */ 

        memoria[280] = 0x00; // Export Table 
        memoria[281] = 0x00; 
        memoria[282] = 0x00; 
        memoria[283] = 0x00; 
        memoria[284] = 0x00; 
        memoria[285] = 0x00; 
        memoria[286] = 0x00; 
        memoria[287] = 0x00; 

        memoria[288] = 0x1C; // Import Table 
        memoria[289] = 0x10; 
        memoria[290] = 0x00; 
        memoria[291] = 0x00; 
        memoria[292] = 0x28; 
        memoria[293] = 0x00; 
        memoria[294] = 0x00; 
        memoria[295] = 0x00; 

        memoria[296] = 0x00; // Resource Table 
        memoria[297] = 0x00; 
        memoria[298] = 0x00; 
        memoria[299] = 0x00; 
        memoria[300] = 0x00;


        memoria[301] = 0x00; 
        memoria[302] = 0x00; 
        memoria[303] = 0x00; 

        memoria[304] = 0x00; // Exception Table 
        memoria[305] = 0x00; 
        memoria[306] = 0x00; 
        memoria[307] = 0x00; 
        memoria[308] = 0x00; 
        memoria[309] = 0x00; 
        memoria[310] = 0x00; 
        memoria[311] = 0x00; 

        memoria[312] = 0x00; // Certificate Table 
        memoria[313] = 0x00; 
        memoria[314] = 0x00; 
        memoria[315] = 0x00; 
        memoria[316] = 0x00; 
        memoria[317] = 0x00; 
        memoria[318] = 0x00; 
        memoria[319] = 0x00; 

        memoria[320] = 0x00; // Base Relocation 
        memoria[321] = 0x00; // Table 
        memoria[322] = 0x00; 
        memoria[323] = 0x00; 
        memoria[324] = 0x00; 
        memoria[325] = 0x00; 
        memoria[326] = 0x00; 
        memoria[327] = 0x00; 

        memoria[328] = 0x00; // Debug 
        memoria[329] = 0x00; 
        memoria[330] = 0x00; 
        memoria[331] = 0x00; 
        memoria[332] = 0x00; 
        memoria[333] = 0x00; 
        memoria[334] = 0x00; 
        memoria[335] = 0x00;
        memoria[336] = 0x00; // Architecture 
        memoria[337] = 0x00; 
        memoria[338] = 0x00; 
        memoria[339] = 0x00; 
        memoria[340] = 0x00; 
        memoria[341] = 0x00; 
        memoria[342] = 0x00; 
        memoria[343] = 0x00; 

        memoria[344] = 0x00; // Global Ptr 
        memoria[345] = 0x00; 
        memoria[346] = 0x00; 
        memoria[347] = 0x00; 
        memoria[348] = 0x00; 
        memoria[349] = 0x00; 
        memoria[350] = 0x00; 
        memoria[351] = 0x00; 

        memoria[352] = 0x00; // TLS Table 
        memoria[353] = 0x00; 
        memoria[354] = 0x00; 
        memoria[355] = 0x00; 
        memoria[356] = 0x00; 
        memoria[357] = 0x00; 
        memoria[358] = 0x00; 
        memoria[359] = 0x00;
        memoria[360] = 0x00; // Load Config Table 
        memoria[361] = 0x00; 
        memoria[362] = 0x00; 
        memoria[363] = 0x00; 
        memoria[364] = 0x00; 
        memoria[365] = 0x00; 
        memoria[366] = 0x00; 
        memoria[367] = 0x00; 

        memoria[368] = 0x00; // Bound Import 
        memoria[369] = 0x00; 
        memoria[370] = 0x00; 
        memoria[371] = 0x00; 
        memoria[372] = 0x00; 
        memoria[373] = 0x00; 
        memoria[374] = 0x00; 
        memoria[375] = 0x00; 

        memoria[376] = 0x00; // IAT 
        memoria[377] = 0x10; 
        memoria[378] = 0x00; 
        memoria[379] = 0x00; 
        memoria[380] = 0x1C; 
        memoria[381] = 0x00; 
        memoria[382] = 0x00; 
        memoria[383] = 0x00; 

        memoria[384] = 0x00; // Delay Import 
        memoria[385] = 0x00; // Descriptor 
        memoria[386] = 0x00; 
        memoria[387] = 0x00; 
        memoria[388] = 0x00; 
        memoria[389] = 0x00; 
        memoria[390] = 0x00; 
        memoria[391] = 0x00; 

        memoria[392] = 0x00; // CLR Runtime 
        memoria[393] = 0x00; // Header 
        memoria[394] = 0x00; 
        memoria[395] = 0x00; 
        memoria[396] = 0x00; 
        memoria[397] = 0x00; 
        memoria[398] = 0x00; 
        memoria[399] = 0x00; 

        memoria[400] = 0x00; // Reserved, must be 
        memoria[401] = 0x00; // zero 
        memoria[402] = 0x00; 
        memoria[403] = 0x00; 
        memoria[404] = 0x00; 
        memoria[405] = 0x00; 
        memoria[406] = 0x00; 
        memoria[407] = 0x00; 

        /* SECTIONS TABLE (40 bytes per entry) */ 

        /* FIRST ENTRY: TEXT HEADER */ 

        memoria[408] = 0x2E; // '.' (Name) 
        memoria[409] = 0x74; // 't' 
        memoria[410] = 0x65; // 'e' 
        memoria[411] = 0x78; // 'x' 
        memoria[412] = 0x74; // 't' 
        memoria[413] = 0x00; 
        memoria[414] = 0x00; 
        memoria[415] = 0x00;
        memoria[416] = 0x24; // Virtual size 
        memoria[417] = 0x05; 
        memoria[418] = 0x00; 
        memoria[419] = 0x00; 

        memoria[420] = 0x00; // Virtual address 
        memoria[421] = 0x10; 
        memoria[422] = 0x00; 
        memoria[423] = 0x00; 

        memoria[424] = 0x00; // Size of raw data 
        memoria[425] = 0x06; 
        memoria[426] = 0x00; 
        memoria[427] = 0x00; 

        memoria[428] = 0x00; // Pointer to 
        memoria[429] = 0x02; // raw data 
        memoria[430] = 0x00; 
        memoria[431] = 0x00; 
        memoria[432] = 0x00; // Pointer to 
        memoria[433] = 0x00; // relocations 
        memoria[434] = 0x00; 
        memoria[435] = 0x00; 

        memoria[436] = 0x00; // Pointer to 
        memoria[437] = 0x00; // line numbers 
        memoria[438] = 0x00; 
        memoria[439] = 0x00; 

        memoria[440] = 0x00; // Number of 
        memoria[441] = 0x00; // relocations 

        memoria[442] = 0x00; // Number of 
        memoria[443] = 0x00; // line numbers 

        memoria[444] = 0x20; // Characteristics 
        memoria[445] = 0x00; // (Readable,  
        memoria[446] = 0x00; // Writeable & 
        memoria[447] = (byte) 0xE0; // Executable
        
        for (int i = 448; i < 512; i++) 
        {
            memoria[i] = 0x00;
        }

        memoria[512] = (byte) 0x6E;
        memoria[513] = (byte) 0x10;
        memoria[514] = (byte) 0x00;
        memoria[515] = (byte) 0x00;
        memoria[516] = (byte) 0x7C;
        memoria[517] = (byte) 0x10;
        memoria[518] = (byte) 0x00;
        memoria[519] = (byte) 0x00;
        memoria[520] = (byte) 0x8C;
        memoria[521] = (byte) 0x10;
        memoria[522] = (byte) 0x00;
        memoria[523] = (byte) 0x00;
        memoria[524] = (byte) 0x98;
        memoria[525] = (byte) 0x10;
        memoria[526] = (byte) 0x00;
        memoria[527] = (byte) 0x00;
        memoria[528] = (byte) 0xA4;
        memoria[529] = (byte) 0x10;
        memoria[530] = (byte) 0x00;
        memoria[531] = (byte) 0x00;
        memoria[532] = (byte) 0xB6;
        memoria[533] = (byte) 0x10;
        memoria[534] = (byte) 0x00;
        memoria[535] = (byte) 0x00;
        memoria[536] = (byte) 0x00;
        memoria[537] = (byte) 0x00;
        memoria[538] = (byte) 0x00;
        memoria[539] = (byte) 0x00;
        memoria[540] = (byte) 0x52;
        memoria[541] = (byte) 0x10;
        memoria[542] = (byte) 0x00;
        memoria[543] = (byte) 0x00;
        memoria[544] = (byte) 0x00;
        memoria[545] = (byte) 0x00;
        memoria[546] = (byte) 0x00;
        memoria[547] = (byte) 0x00;
        memoria[548] = (byte) 0x00;
        memoria[549] = (byte) 0x00;
        memoria[550] = (byte) 0x00;
        memoria[551] = (byte) 0x00;
        memoria[552] = (byte) 0x44;
        memoria[553] = (byte) 0x10;
        memoria[554] = (byte) 0x00;
        memoria[555] = (byte) 0x00;
        memoria[556] = (byte) 0x00;
        memoria[557] = (byte) 0x10;
        memoria[558] = (byte) 0x00;
        memoria[559] = (byte) 0x00;
        memoria[560] = (byte) 0x00;
        memoria[561] = (byte) 0x00;
        memoria[562] = (byte) 0x00;
        memoria[563] = (byte) 0x00;
        memoria[564] = (byte) 0x00;
        memoria[565] = (byte) 0x00;
        memoria[566] = (byte) 0x00;
        memoria[567] = (byte) 0x00;
        memoria[568] = (byte) 0x00;
        memoria[569] = (byte) 0x00;
        memoria[570] = (byte) 0x00;
        memoria[571] = (byte) 0x00;
        memoria[572] = (byte) 0x00;
        memoria[573] = (byte) 0x00;
        memoria[574] = (byte) 0x00;
        memoria[575] = (byte) 0x00;
        memoria[576] = (byte) 0x00;
        memoria[577] = (byte) 0x00;
        memoria[578] = (byte) 0x00;
        memoria[579] = (byte) 0x00;
        memoria[580] = (byte) 0x4B;
        memoria[581] = (byte) 0x45;
        memoria[582] = (byte) 0x52;
        memoria[583] = (byte) 0x4E;
        memoria[584] = (byte) 0x45;
        memoria[585] = (byte) 0x4C;
        memoria[586] = (byte) 0x33;
        memoria[587] = (byte) 0x32;
        memoria[588] = (byte) 0x2E;
        memoria[589] = (byte) 0x64;
        memoria[590] = (byte) 0x6C;
        memoria[591] = (byte) 0x6C;
        memoria[592] = (byte) 0x00;
        memoria[593] = (byte) 0x00;
        memoria[594] = (byte) 0x6E;
        memoria[595] = (byte) 0x10;
        memoria[596] = (byte) 0x00;
        memoria[597] = (byte) 0x00;
        memoria[598] = (byte) 0x7C;
        memoria[599] = (byte) 0x10;
        memoria[600] = (byte) 0x00;
        memoria[601] = (byte) 0x00;
        memoria[602] = (byte) 0x8C;
        memoria[603] = (byte) 0x10;
        memoria[604] = (byte) 0x00;
        memoria[605] = (byte) 0x00;
        memoria[606] = (byte) 0x98;
        memoria[607] = (byte) 0x10;
        memoria[608] = (byte) 0x00;
        memoria[609] = (byte) 0x00;
        memoria[610] = (byte) 0xA4;
        memoria[611] = (byte) 0x10;
        memoria[612] = (byte) 0x00;
        memoria[613] = (byte) 0x00;
        memoria[614] = (byte) 0xB6;
        memoria[615] = (byte) 0x10;
        memoria[616] = (byte) 0x00;
        memoria[617] = (byte) 0x00;
        memoria[618] = (byte) 0x00;
        memoria[619] = (byte) 0x00;
        memoria[620] = (byte) 0x00;
        memoria[621] = (byte) 0x00;
        memoria[622] = (byte) 0x00;
        memoria[623] = (byte) 0x00;
        memoria[624] = (byte) 0x45;
        memoria[625] = (byte) 0x78;
        memoria[626] = (byte) 0x69;
        memoria[627] = (byte) 0x74;
        memoria[628] = (byte) 0x50;
        memoria[629] = (byte) 0x72;
        memoria[630] = (byte) 0x6F;
        memoria[631] = (byte) 0x63;
        memoria[632] = (byte) 0x65;
        memoria[633] = (byte) 0x73;
        memoria[634] = (byte) 0x73;
        memoria[635] = (byte) 0x00;
        memoria[636] = (byte) 0x00;
        memoria[637] = (byte) 0x00;
        memoria[638] = (byte) 0x47;
        memoria[639] = (byte) 0x65;
        memoria[640] = (byte) 0x74;
        memoria[641] = (byte) 0x53;
        memoria[642] = (byte) 0x74;
        memoria[643] = (byte) 0x64;
        memoria[644] = (byte) 0x48;
        memoria[645] = (byte) 0x61;
        memoria[646] = (byte) 0x6E;
        memoria[647] = (byte) 0x64;
        memoria[648] = (byte) 0x6C;
        memoria[649] = (byte) 0x65;
        memoria[650] = (byte) 0x00;
        memoria[651] = (byte) 0x00;
        memoria[652] = (byte) 0x00;
        memoria[653] = (byte) 0x00;
        memoria[654] = (byte) 0x52;
        memoria[655] = (byte) 0x65;
        memoria[656] = (byte) 0x61;
        memoria[657] = (byte) 0x64;
        memoria[658] = (byte) 0x46;
        memoria[659] = (byte) 0x69;
        memoria[660] = (byte) 0x6C;
        memoria[661] = (byte) 0x65;
        memoria[662] = (byte) 0x00;
        memoria[663] = (byte) 0x00;
        memoria[664] = (byte) 0x00;
        memoria[665] = (byte) 0x00;
        memoria[666] = (byte) 0x57;
        memoria[667] = (byte) 0x72;
        memoria[668] = (byte) 0x69;
        memoria[669] = (byte) 0x74;
        memoria[670] = (byte) 0x65;
        memoria[671] = (byte) 0x46;
        memoria[672] = (byte) 0x69;
        memoria[673] = (byte) 0x6C;
        memoria[674] = (byte) 0x65;
        memoria[675] = (byte) 0x00;
        memoria[676] = (byte) 0x00;
        memoria[677] = (byte) 0x00;
        memoria[678] = (byte) 0x47;
        memoria[679] = (byte) 0x65;
        memoria[680] = (byte) 0x74;
        memoria[681] = (byte) 0x43;
        memoria[682] = (byte) 0x6F;
        memoria[683] = (byte) 0x6E;
        memoria[684] = (byte) 0x73;
        memoria[685] = (byte) 0x6F;
        memoria[686] = (byte) 0x6C;
        memoria[687] = (byte) 0x65;
        memoria[688] = (byte) 0x4D;
        memoria[689] = (byte) 0x6F;
        memoria[690] = (byte) 0x64;
        memoria[691] = (byte) 0x65;
        memoria[692] = (byte) 0x00;
        memoria[693] = (byte) 0x00;
        memoria[694] = (byte) 0x00;
        memoria[695] = (byte) 0x00;
        memoria[696] = (byte) 0x53;
        memoria[697] = (byte) 0x65;
        memoria[698] = (byte) 0x74;
        memoria[699] = (byte) 0x43;
        memoria[700] = (byte) 0x6F;
        memoria[701] = (byte) 0x6E;
        memoria[702] = (byte) 0x73;
        memoria[703] = (byte) 0x6F;
        memoria[704] = (byte) 0x6C;
        memoria[705] = (byte) 0x65;
        memoria[706] = (byte) 0x4D;
        memoria[707] = (byte) 0x6F;
        memoria[708] = (byte) 0x64;
        memoria[709] = (byte) 0x65;
        memoria[710] = (byte) 0x00;
        memoria[711] = (byte) 0x00;
        memoria[712] = (byte) 0x00;
        memoria[713] = (byte) 0x00;
        memoria[714] = (byte) 0x00;
        memoria[715] = (byte) 0x00;
        memoria[716] = (byte) 0x00;
        memoria[717] = (byte) 0x00;
        memoria[718] = (byte) 0x00;
        memoria[719] = (byte) 0x00;
        memoria[720] = (byte) 0x50;
        memoria[721] = (byte) 0xA2;
        memoria[722] = (byte) 0x1C;
        memoria[723] = (byte) 0x11;
        memoria[724] = (byte) 0x40;
        memoria[725] = (byte) 0x00;
        memoria[726] = (byte) 0x31;
        memoria[727] = (byte) 0xC0;
        memoria[728] = (byte) 0x03;
        memoria[729] = (byte) 0x05;
        memoria[730] = (byte) 0x2C;
        memoria[731] = (byte) 0x11;
        memoria[732] = (byte) 0x40;
        memoria[733] = (byte) 0x00;
        memoria[734] = (byte) 0x75;
        memoria[735] = (byte) 0x0D;
        memoria[736] = (byte) 0x6A;
        memoria[737] = (byte) 0xF5;
        memoria[738] = (byte) 0xFF;
        memoria[739] = (byte) 0x15;
        memoria[740] = (byte) 0x04;
        memoria[741] = (byte) 0x10;
        memoria[742] = (byte) 0x40;
        memoria[743] = (byte) 0x00;
        memoria[744] = (byte) 0xA3;
        memoria[745] = (byte) 0x2C;
        memoria[746] = (byte) 0x11;
        memoria[747] = (byte) 0x40;
        memoria[748] = (byte) 0x00;
        memoria[749] = (byte) 0x6A;
        memoria[750] = (byte) 0x00;
        memoria[751] = (byte) 0x68;
        memoria[752] = (byte) 0x30;
        memoria[753] = (byte) 0x11;
        memoria[754] = (byte) 0x40;
        memoria[755] = (byte) 0x00;
        memoria[756] = (byte) 0x6A;
        memoria[757] = (byte) 0x01;
        memoria[758] = (byte) 0x68;
        memoria[759] = (byte) 0x1C;
        memoria[760] = (byte) 0x11;
        memoria[761] = (byte) 0x40;
        memoria[762] = (byte) 0x00;
        memoria[763] = (byte) 0x50;
        memoria[764] = (byte) 0xFF;
        memoria[765] = (byte) 0x15;
        memoria[766] = (byte) 0x0C;
        memoria[767] = (byte) 0x10;
        memoria[768] = (byte) 0x40;
        memoria[769] = (byte) 0x00;
        memoria[770] = (byte) 0x09;
        memoria[771] = (byte) 0xC0;
        memoria[772] = (byte) 0x75;
        memoria[773] = (byte) 0x08;
        memoria[774] = (byte) 0x6A;
        memoria[775] = (byte) 0x00;
        memoria[776] = (byte) 0xFF;
        memoria[777] = (byte) 0x15;
        memoria[778] = (byte) 0x00;
        memoria[779] = (byte) 0x10;
        memoria[780] = (byte) 0x40;
        memoria[781] = (byte) 0x00;
        memoria[782] = (byte) 0x81;
        memoria[783] = (byte) 0x3D;
        memoria[784] = (byte) 0x30;
        memoria[785] = (byte) 0x11;
        memoria[786] = (byte) 0x40;
        memoria[787] = (byte) 0x00;
        memoria[788] = (byte) 0x01;
        memoria[789] = (byte) 0x00;
        memoria[790] = (byte) 0x00;
        memoria[791] = (byte) 0x00;
        memoria[792] = (byte) 0x75;
        memoria[793] = (byte) 0xEC;
        memoria[794] = (byte) 0x58;
        memoria[795] = (byte) 0xC3;
        memoria[796] = (byte) 0x00;
        memoria[797] = (byte) 0x57;
        memoria[798] = (byte) 0x72;
        memoria[799] = (byte) 0x69;
        memoria[800] = (byte) 0x74;
        memoria[801] = (byte) 0x65;
        memoria[802] = (byte) 0x20;
        memoria[803] = (byte) 0x65;
        memoria[804] = (byte) 0x72;
        memoria[805] = (byte) 0x72;
        memoria[806] = (byte) 0x6F;
        memoria[807] = (byte) 0x72;
        memoria[808] = (byte) 0x00;
        memoria[809] = (byte) 0x00;
        memoria[810] = (byte) 0x00;
        memoria[811] = (byte) 0x00;
        memoria[812] = (byte) 0x00;
        memoria[813] = (byte) 0x00;
        memoria[814] = (byte) 0x00;
        memoria[815] = (byte) 0x00;
        memoria[816] = (byte) 0x00;
        memoria[817] = (byte) 0x00;
        memoria[818] = (byte) 0x00;
        memoria[819] = (byte) 0x00;
        memoria[820] = (byte) 0x00;
        memoria[821] = (byte) 0x00;
        memoria[822] = (byte) 0x00;
        memoria[823] = (byte) 0x00;
        memoria[824] = (byte) 0x00;
        memoria[825] = (byte) 0x00;
        memoria[826] = (byte) 0x00;
        memoria[827] = (byte) 0x00;
        memoria[828] = (byte) 0x00;
        memoria[829] = (byte) 0x00;
        memoria[830] = (byte) 0x00;
        memoria[831] = (byte) 0x00;
        memoria[832] = (byte) 0x60;
        memoria[833] = (byte) 0x31;
        memoria[834] = (byte) 0xC0;
        memoria[835] = (byte) 0x03;
        memoria[836] = (byte) 0x05;
        memoria[837] = (byte) 0xCC;
        memoria[838] = (byte) 0x11;
        memoria[839] = (byte) 0x40;
        memoria[840] = (byte) 0x00;
        memoria[841] = (byte) 0x75;
        memoria[842] = (byte) 0x37;
        memoria[843] = (byte) 0x6A;
        memoria[844] = (byte) 0xF6;
        memoria[845] = (byte) 0xFF;
        memoria[846] = (byte) 0x15;
        memoria[847] = (byte) 0x04;
        memoria[848] = (byte) 0x10;
        memoria[849] = (byte) 0x40;
        memoria[850] = (byte) 0x00;
        memoria[851] = (byte) 0xA3;
        memoria[852] = (byte) 0xCC;
        memoria[853] = (byte) 0x11;
        memoria[854] = (byte) 0x40;
        memoria[855] = (byte) 0x00;
        memoria[856] = (byte) 0x68;
        memoria[857] = (byte) 0xD0;
        memoria[858] = (byte) 0x11;
        memoria[859] = (byte) 0x40;
        memoria[860] = (byte) 0x00;
        memoria[861] = (byte) 0x50;
        memoria[862] = (byte) 0xFF;
        memoria[863] = (byte) 0x15;
        memoria[864] = (byte) 0x10;
        memoria[865] = (byte) 0x10;
        memoria[866] = (byte) 0x40;
        memoria[867] = (byte) 0x00;
        memoria[868] = (byte) 0x80;
        memoria[869] = (byte) 0x25;
        memoria[870] = (byte) 0xD0;
        memoria[871] = (byte) 0x11;
        memoria[872] = (byte) 0x40;
        memoria[873] = (byte) 0x00;
        memoria[874] = (byte) 0xF9;
        memoria[875] = (byte) 0xFF;
        memoria[876] = (byte) 0x35;
        memoria[877] = (byte) 0xD0;
        memoria[878] = (byte) 0x11;
        memoria[879] = (byte) 0x40;
        memoria[880] = (byte) 0x00;
        memoria[881] = (byte) 0xFF;
        memoria[882] = (byte) 0x35;
        memoria[883] = (byte) 0xCC;
        memoria[884] = (byte) 0x11;
        memoria[885] = (byte) 0x40;
        memoria[886] = (byte) 0x00;
        memoria[887] = (byte) 0xFF;
        memoria[888] = (byte) 0x15;
        memoria[889] = (byte) 0x14;
        memoria[890] = (byte) 0x10;
        memoria[891] = (byte) 0x40;
        memoria[892] = (byte) 0x00;
        memoria[893] = (byte) 0xA1;
        memoria[894] = (byte) 0xCC;
        memoria[895] = (byte) 0x11;
        memoria[896] = (byte) 0x40;
        memoria[897] = (byte) 0x00;
        memoria[898] = (byte) 0x6A;
        memoria[899] = (byte) 0x00;
        memoria[900] = (byte) 0x68;
        memoria[901] = (byte) 0xD4;
        memoria[902] = (byte) 0x11;
        memoria[903] = (byte) 0x40;
        memoria[904] = (byte) 0x00;
        memoria[905] = (byte) 0x6A;
        memoria[906] = (byte) 0x01;
        memoria[907] = (byte) 0x68;
        memoria[908] = (byte) 0xBE;
        memoria[909] = (byte) 0x11;
        memoria[910] = (byte) 0x40;
        memoria[911] = (byte) 0x00;
        memoria[912] = (byte) 0x50;
        memoria[913] = (byte) 0xFF;
        memoria[914] = (byte) 0x15;
        memoria[915] = (byte) 0x08;
        memoria[916] = (byte) 0x10;
        memoria[917] = (byte) 0x40;
        memoria[918] = (byte) 0x00;
        memoria[919] = (byte) 0x09;
        memoria[920] = (byte) 0xC0;
        memoria[921] = (byte) 0x61;
        memoria[922] = (byte) 0x90;
        memoria[923] = (byte) 0x75;
        memoria[924] = (byte) 0x08;
        memoria[925] = (byte) 0x6A;
        memoria[926] = (byte) 0x00;
        memoria[927] = (byte) 0xFF;
        memoria[928] = (byte) 0x15;
        memoria[929] = (byte) 0x00;
        memoria[930] = (byte) 0x10;
        memoria[931] = (byte) 0x40;
        memoria[932] = (byte) 0x00;
        memoria[933] = (byte) 0x0F;
        memoria[934] = (byte) 0xB6;
        memoria[935] = (byte) 0x05;
        memoria[936] = (byte) 0xBE;
        memoria[937] = (byte) 0x11;
        memoria[938] = (byte) 0x40;
        memoria[939] = (byte) 0x00;
        memoria[940] = (byte) 0x81;
        memoria[941] = (byte) 0x3D;
        memoria[942] = (byte) 0xD4;
        memoria[943] = (byte) 0x11;
        memoria[944] = (byte) 0x40;
        memoria[945] = (byte) 0x00;
        memoria[946] = (byte) 0x01;
        memoria[947] = (byte) 0x00;
        memoria[948] = (byte) 0x00;
        memoria[949] = (byte) 0x00;
        memoria[950] = (byte) 0x74;
        memoria[951] = (byte) 0x05;
        memoria[952] = (byte) 0xB8;
        memoria[953] = (byte) 0xFF;
        memoria[954] = (byte) 0xFF;
        memoria[955] = (byte) 0xFF;
        memoria[956] = (byte) 0xFF;
        memoria[957] = (byte) 0xC3;
        memoria[958] = (byte) 0x00;
        memoria[959] = (byte) 0x52;
        memoria[960] = (byte) 0x65;
        memoria[961] = (byte) 0x61;
        memoria[962] = (byte) 0x64;
        memoria[963] = (byte) 0x20;
        memoria[964] = (byte) 0x65;
        memoria[965] = (byte) 0x72;
        memoria[966] = (byte) 0x72;
        memoria[967] = (byte) 0x6F;
        memoria[968] = (byte) 0x72;
        memoria[969] = (byte) 0x00;
        memoria[970] = (byte) 0x00;
        memoria[971] = (byte) 0x00;
        memoria[972] = (byte) 0x00;
        memoria[973] = (byte) 0x00;
        memoria[974] = (byte) 0x00;
        memoria[975] = (byte) 0x00;
        memoria[976] = (byte) 0x00;
        memoria[977] = (byte) 0x00;
        memoria[978] = (byte) 0x00;
        memoria[979] = (byte) 0x00;
        memoria[980] = (byte) 0x00;
        memoria[981] = (byte) 0x00;
        memoria[982] = (byte) 0x00;
        memoria[983] = (byte) 0x00;
        memoria[984] = (byte) 0x00;
        memoria[985] = (byte) 0x00;
        memoria[986] = (byte) 0x00;
        memoria[987] = (byte) 0x00;
        memoria[988] = (byte) 0x00;
        memoria[989] = (byte) 0x00;
        memoria[990] = (byte) 0x00;
        memoria[991] = (byte) 0x00;
        memoria[992] = (byte) 0x60;
        memoria[993] = (byte) 0x89;
        memoria[994] = (byte) 0xC6;
        memoria[995] = (byte) 0x30;
        memoria[996] = (byte) 0xC0;
        memoria[997] = (byte) 0x02;
        memoria[998] = (byte) 0x06;
        memoria[999] = (byte) 0x74;
        memoria[1000] = (byte) 0x08;
        memoria[1001] = (byte) 0x46;
        memoria[1002] = (byte) 0xE8;
        memoria[1003] = (byte) 0xE1;
        memoria[1004] = (byte) 0xFE;
        memoria[1005] = (byte) 0xFF;
        memoria[1006] = (byte) 0xFF;
        memoria[1007] = (byte) 0xEB;
        memoria[1008] = (byte) 0xF2;
        memoria[1009] = (byte) 0x61;
        memoria[1010] = (byte) 0x90;
        memoria[1011] = (byte) 0xC3;
        memoria[1012] = (byte) 0x00;
        memoria[1013] = (byte) 0x00;
        memoria[1014] = (byte) 0x00;
        memoria[1015] = (byte) 0x00;
        memoria[1016] = (byte) 0x00;
        memoria[1017] = (byte) 0x00;
        memoria[1018] = (byte) 0x00;
        memoria[1019] = (byte) 0x00;
        memoria[1020] = (byte) 0x00;
        memoria[1021] = (byte) 0x00;
        memoria[1022] = (byte) 0x00;
        memoria[1023] = (byte) 0x00;
        memoria[1024] = (byte) 0x04;
        memoria[1025] = (byte) 0x30;
        memoria[1026] = (byte) 0xE8;
        memoria[1027] = (byte) 0xC9;
        memoria[1028] = (byte) 0xFE;
        memoria[1029] = (byte) 0xFF;
        memoria[1030] = (byte) 0xFF;
        memoria[1031] = (byte) 0xC3;
        memoria[1032] = (byte) 0x00;
        memoria[1033] = (byte) 0x00;
        memoria[1034] = (byte) 0x00;
        memoria[1035] = (byte) 0x00;
        memoria[1036] = (byte) 0x00;
        memoria[1037] = (byte) 0x00;
        memoria[1038] = (byte) 0x00;
        memoria[1039] = (byte) 0x00;
        memoria[1040] = (byte) 0xB0;
        memoria[1041] = (byte) 0x0D;
        memoria[1042] = (byte) 0xE8;
        memoria[1043] = (byte) 0xB9;
        memoria[1044] = (byte) 0xFE;
        memoria[1045] = (byte) 0xFF;
        memoria[1046] = (byte) 0xFF;
        memoria[1047] = (byte) 0xB0;
        memoria[1048] = (byte) 0x0A;
        memoria[1049] = (byte) 0xE8;
        memoria[1050] = (byte) 0xB2;
        memoria[1051] = (byte) 0xFE;
        memoria[1052] = (byte) 0xFF;
        memoria[1053] = (byte) 0xFF;
        memoria[1054] = (byte) 0xC3;
        memoria[1055] = (byte) 0x00;
        memoria[1056] = (byte) 0x3D;
        memoria[1057] = (byte) 0x00;
        memoria[1058] = (byte) 0x00;
        memoria[1059] = (byte) 0x00;
        memoria[1060] = (byte) 0x80;
        memoria[1061] = (byte) 0x75;
        memoria[1062] = (byte) 0x4E;
        memoria[1063] = (byte) 0xB0;
        memoria[1064] = (byte) 0x2D;
        memoria[1065] = (byte) 0xE8;
        memoria[1066] = (byte) 0xA2;
        memoria[1067] = (byte) 0xFE;
        memoria[1068] = (byte) 0xFF;
        memoria[1069] = (byte) 0xFF;
        memoria[1070] = (byte) 0xB0;
        memoria[1071] = (byte) 0x02;
        memoria[1072] = (byte) 0xE8;
        memoria[1073] = (byte) 0xCB;
        memoria[1074] = (byte) 0xFF;
        memoria[1075] = (byte) 0xFF;
        memoria[1076] = (byte) 0xFF;
        memoria[1077] = (byte) 0xB0;
        memoria[1078] = (byte) 0x01;
        memoria[1079] = (byte) 0xE8;
        memoria[1080] = (byte) 0xC4;
        memoria[1081] = (byte) 0xFF;
        memoria[1082] = (byte) 0xFF;
        memoria[1083] = (byte) 0xFF;
        memoria[1084] = (byte) 0xB0;
        memoria[1085] = (byte) 0x04;
        memoria[1086] = (byte) 0xE8;
        memoria[1087] = (byte) 0xBD;
        memoria[1088] = (byte) 0xFF;
        memoria[1089] = (byte) 0xFF;
        memoria[1090] = (byte) 0xFF;
        memoria[1091] = (byte) 0xB0;
        memoria[1092] = (byte) 0x07;
        memoria[1093] = (byte) 0xE8;
        memoria[1094] = (byte) 0xB6;
        memoria[1095] = (byte) 0xFF;
        memoria[1096] = (byte) 0xFF;
        memoria[1097] = (byte) 0xFF;
        memoria[1098] = (byte) 0xB0;
        memoria[1099] = (byte) 0x04;
        memoria[1100] = (byte) 0xE8;
        memoria[1101] = (byte) 0xAF;
        memoria[1102] = (byte) 0xFF;
        memoria[1103] = (byte) 0xFF;
        memoria[1104] = (byte) 0xFF;
        memoria[1105] = (byte) 0xB0;
        memoria[1106] = (byte) 0x08;
        memoria[1107] = (byte) 0xE8;
        memoria[1108] = (byte) 0xA8;
        memoria[1109] = (byte) 0xFF;
        memoria[1110] = (byte) 0xFF;
        memoria[1111] = (byte) 0xFF;
        memoria[1112] = (byte) 0xB0;
        memoria[1113] = (byte) 0x03;
        memoria[1114] = (byte) 0xE8;
        memoria[1115] = (byte) 0xA1;
        memoria[1116] = (byte) 0xFF;
        memoria[1117] = (byte) 0xFF;
        memoria[1118] = (byte) 0xFF;
        memoria[1119] = (byte) 0xB0;
        memoria[1120] = (byte) 0x06;
        memoria[1121] = (byte) 0xE8;
        memoria[1122] = (byte) 0x9A;
        memoria[1123] = (byte) 0xFF;
        memoria[1124] = (byte) 0xFF;
        memoria[1125] = (byte) 0xFF;
        memoria[1126] = (byte) 0xB0;
        memoria[1127] = (byte) 0x04;
        memoria[1128] = (byte) 0xE8;
        memoria[1129] = (byte) 0x93;
        memoria[1130] = (byte) 0xFF;
        memoria[1131] = (byte) 0xFF;
        memoria[1132] = (byte) 0xFF;
        memoria[1133] = (byte) 0xB0;
        memoria[1134] = (byte) 0x08;
        memoria[1135] = (byte) 0xE8;
        memoria[1136] = (byte) 0x8C;
        memoria[1137] = (byte) 0xFF;
        memoria[1138] = (byte) 0xFF;
        memoria[1139] = (byte) 0xFF;
        memoria[1140] = (byte) 0xC3;
        memoria[1141] = (byte) 0x3D;
        memoria[1142] = (byte) 0x00;
        memoria[1143] = (byte) 0x00;
        memoria[1144] = (byte) 0x00;
        memoria[1145] = (byte) 0x00;
        memoria[1146] = (byte) 0x7D;
        memoria[1147] = (byte) 0x0B;
        memoria[1148] = (byte) 0x50;
        memoria[1149] = (byte) 0xB0;
        memoria[1150] = (byte) 0x2D;
        memoria[1151] = (byte) 0xE8;
        memoria[1152] = (byte) 0x4C;
        memoria[1153] = (byte) 0xFE;
        memoria[1154] = (byte) 0xFF;
        memoria[1155] = (byte) 0xFF;
        memoria[1156] = (byte) 0x58;
        memoria[1157] = (byte) 0xF7;
        memoria[1158] = (byte) 0xD8;
        memoria[1159] = (byte) 0x3D;
        memoria[1160] = (byte) 0x0A;
        memoria[1161] = (byte) 0x00;
        memoria[1162] = (byte) 0x00;
        memoria[1163] = (byte) 0x00;
        memoria[1164] = (byte) 0x0F;
        memoria[1165] = (byte) 0x8C;
        memoria[1166] = (byte) 0xEF;
        memoria[1167] = (byte) 0x00;
        memoria[1168] = (byte) 0x00;
        memoria[1169] = (byte) 0x00;
        memoria[1170] = (byte) 0x3D;
        memoria[1171] = (byte) 0x64;
        memoria[1172] = (byte) 0x00;
        memoria[1173] = (byte) 0x00;
        memoria[1174] = (byte) 0x00;
        memoria[1175] = (byte) 0x0F;
        memoria[1176] = (byte) 0x8C;
        memoria[1177] = (byte) 0xD1;
        memoria[1178] = (byte) 0x00;
        memoria[1179] = (byte) 0x00;
        memoria[1180] = (byte) 0x00;
        memoria[1181] = (byte) 0x3D;
        memoria[1182] = (byte) 0xE8;
        memoria[1183] = (byte) 0x03;
        memoria[1184] = (byte) 0x00;
        memoria[1185] = (byte) 0x00;
        memoria[1186] = (byte) 0x0F;
        memoria[1187] = (byte) 0x8C;
        memoria[1188] = (byte) 0xB3;
        memoria[1189] = (byte) 0x00;
        memoria[1190] = (byte) 0x00;
        memoria[1191] = (byte) 0x00;
        memoria[1192] = (byte) 0x3D;
        memoria[1193] = (byte) 0x10;
        memoria[1194] = (byte) 0x27;
        memoria[1195] = (byte) 0x00;
        memoria[1196] = (byte) 0x00;
        memoria[1197] = (byte) 0x0F;
        memoria[1198] = (byte) 0x8C;
        memoria[1199] = (byte) 0x95;
        memoria[1200] = (byte) 0x00;
        memoria[1201] = (byte) 0x00;
        memoria[1202] = (byte) 0x00;
        memoria[1203] = (byte) 0x3D;
        memoria[1204] = (byte) 0xA0;
        memoria[1205] = (byte) 0x86;
        memoria[1206] = (byte) 0x01;
        memoria[1207] = (byte) 0x00;
        memoria[1208] = (byte) 0x7C;
        memoria[1209] = (byte) 0x7B;
        memoria[1210] = (byte) 0x3D;
        memoria[1211] = (byte) 0x40;
        memoria[1212] = (byte) 0x42;
        memoria[1213] = (byte) 0x0F;
        memoria[1214] = (byte) 0x00;
        memoria[1215] = (byte) 0x7C;
        memoria[1216] = (byte) 0x61;
        memoria[1217] = (byte) 0x3D;
        memoria[1218] = (byte) 0x80;
        memoria[1219] = (byte) 0x96;
        memoria[1220] = (byte) 0x98;
        memoria[1221] = (byte) 0x00;
        memoria[1222] = (byte) 0x7C;
        memoria[1223] = (byte) 0x47;
        memoria[1224] = (byte) 0x3D;
        memoria[1225] = (byte) 0x00;
        memoria[1226] = (byte) 0xE1;
        memoria[1227] = (byte) 0xF5;
        memoria[1228] = (byte) 0x05;
        memoria[1229] = (byte) 0x7C;
        memoria[1230] = (byte) 0x2D;
        memoria[1231] = (byte) 0x3D;
        memoria[1232] = (byte) 0x00;
        memoria[1233] = (byte) 0xCA;
        memoria[1234] = (byte) 0x9A;
        memoria[1235] = (byte) 0x3B;
        memoria[1236] = (byte) 0x7C;
        memoria[1237] = (byte) 0x13;
        memoria[1238] = (byte) 0xBA;
        memoria[1239] = (byte) 0x00;
        memoria[1240] = (byte) 0x00;
        memoria[1241] = (byte) 0x00;
        memoria[1242] = (byte) 0x00;
        memoria[1243] = (byte) 0xBB;
        memoria[1244] = (byte) 0x00;
        memoria[1245] = (byte) 0xCA;
        memoria[1246] = (byte) 0x9A;
        memoria[1247] = (byte) 0x3B;
        memoria[1248] = (byte) 0xF7;
        memoria[1249] = (byte) 0xFB;
        memoria[1250] = (byte) 0x52;
        memoria[1251] = (byte) 0xE8;
        memoria[1252] = (byte) 0x18;
        memoria[1253] = (byte) 0xFF;
        memoria[1254] = (byte) 0xFF;
        memoria[1255] = (byte) 0xFF;
        memoria[1256] = (byte) 0x58;
        memoria[1257] = (byte) 0xBA;
        memoria[1258] = (byte) 0x00;
        memoria[1259] = (byte) 0x00;
        memoria[1260] = (byte) 0x00;
        memoria[1261] = (byte) 0x00;
        memoria[1262] = (byte) 0xBB;
        memoria[1263] = (byte) 0x00;
        memoria[1264] = (byte) 0xE1;
        memoria[1265] = (byte) 0xF5;
        memoria[1266] = (byte) 0x05;
        memoria[1267] = (byte) 0xF7;
        memoria[1268] = (byte) 0xFB;
        memoria[1269] = (byte) 0x52;
        memoria[1270] = (byte) 0xE8;
        memoria[1271] = (byte) 0x05;
        memoria[1272] = (byte) 0xFF;
        memoria[1273] = (byte) 0xFF;
        memoria[1274] = (byte) 0xFF;
        memoria[1275] = (byte) 0x58;
        memoria[1276] = (byte) 0xBA;
        memoria[1277] = (byte) 0x00;
        memoria[1278] = (byte) 0x00;
        memoria[1279] = (byte) 0x00;
        memoria[1280] = (byte) 0x00;
        memoria[1281] = (byte) 0xBB;
        memoria[1282] = (byte) 0x80;
        memoria[1283] = (byte) 0x96;
        memoria[1284] = (byte) 0x98;
        memoria[1285] = (byte) 0x00;
        memoria[1286] = (byte) 0xF7;
        memoria[1287] = (byte) 0xFB;
        memoria[1288] = (byte) 0x52;
        memoria[1289] = (byte) 0xE8;
        memoria[1290] = (byte) 0xF2;
        memoria[1291] = (byte) 0xFE;
        memoria[1292] = (byte) 0xFF;
        memoria[1293] = (byte) 0xFF;
        memoria[1294] = (byte) 0x58;
        memoria[1295] = (byte) 0xBA;
        memoria[1296] = (byte) 0x00;
        memoria[1297] = (byte) 0x00;
        memoria[1298] = (byte) 0x00;
        memoria[1299] = (byte) 0x00;
        memoria[1300] = (byte) 0xBB;
        memoria[1301] = (byte) 0x40;
        memoria[1302] = (byte) 0x42;
        memoria[1303] = (byte) 0x0F;
        memoria[1304] = (byte) 0x00;
        memoria[1305] = (byte) 0xF7;
        memoria[1306] = (byte) 0xFB;
        memoria[1307] = (byte) 0x52;
        memoria[1308] = (byte) 0xE8;
        memoria[1309] = (byte) 0xDF;
        memoria[1310] = (byte) 0xFE;
        memoria[1311] = (byte) 0xFF;
        memoria[1312] = (byte) 0xFF;
        memoria[1313] = (byte) 0x58;
        memoria[1314] = (byte) 0xBA;
        memoria[1315] = (byte) 0x00;
        memoria[1316] = (byte) 0x00;
        memoria[1317] = (byte) 0x00;
        memoria[1318] = (byte) 0x00;
        memoria[1319] = (byte) 0xBB;
        memoria[1320] = (byte) 0xA0;
        memoria[1321] = (byte) 0x86;
        memoria[1322] = (byte) 0x01;
        memoria[1323] = (byte) 0x00;
        memoria[1324] = (byte) 0xF7;
        memoria[1325] = (byte) 0xFB;
        memoria[1326] = (byte) 0x52;
        memoria[1327] = (byte) 0xE8;
        memoria[1328] = (byte) 0xCC;
        memoria[1329] = (byte) 0xFE;
        memoria[1330] = (byte) 0xFF;
        memoria[1331] = (byte) 0xFF;
        memoria[1332] = (byte) 0x58;
        memoria[1333] = (byte) 0xBA;
        memoria[1334] = (byte) 0x00;
        memoria[1335] = (byte) 0x00;
        memoria[1336] = (byte) 0x00;
        memoria[1337] = (byte) 0x00;
        memoria[1338] = (byte) 0xBB;
        memoria[1339] = (byte) 0x10;
        memoria[1340] = (byte) 0x27;
        memoria[1341] = (byte) 0x00;
        memoria[1342] = (byte) 0x00;
        memoria[1343] = (byte) 0xF7;
        memoria[1344] = (byte) 0xFB;
        memoria[1345] = (byte) 0x52;
        memoria[1346] = (byte) 0xE8;
        memoria[1347] = (byte) 0xB9;
        memoria[1348] = (byte) 0xFE;
        memoria[1349] = (byte) 0xFF;
        memoria[1350] = (byte) 0xFF;
        memoria[1351] = (byte) 0x58;
        memoria[1352] = (byte) 0xBA;
        memoria[1353] = (byte) 0x00;
        memoria[1354] = (byte) 0x00;
        memoria[1355] = (byte) 0x00;
        memoria[1356] = (byte) 0x00;
        memoria[1357] = (byte) 0xBB;
        memoria[1358] = (byte) 0xE8;
        memoria[1359] = (byte) 0x03;
        memoria[1360] = (byte) 0x00;
        memoria[1361] = (byte) 0x00;
        memoria[1362] = (byte) 0xF7;
        memoria[1363] = (byte) 0xFB;
        memoria[1364] = (byte) 0x52;
        memoria[1365] = (byte) 0xE8;
        memoria[1366] = (byte) 0xA6;
        memoria[1367] = (byte) 0xFE;
        memoria[1368] = (byte) 0xFF;
        memoria[1369] = (byte) 0xFF;
        memoria[1370] = (byte) 0x58;
        memoria[1371] = (byte) 0xBA;
        memoria[1372] = (byte) 0x00;
        memoria[1373] = (byte) 0x00;
        memoria[1374] = (byte) 0x00;
        memoria[1375] = (byte) 0x00;
        memoria[1376] = (byte) 0xBB;
        memoria[1377] = (byte) 0x64;
        memoria[1378] = (byte) 0x00;
        memoria[1379] = (byte) 0x00;
        memoria[1380] = (byte) 0x00;
        memoria[1381] = (byte) 0xF7;
        memoria[1382] = (byte) 0xFB;
        memoria[1383] = (byte) 0x52;
        memoria[1384] = (byte) 0xE8;
        memoria[1385] = (byte) 0x93;
        memoria[1386] = (byte) 0xFE;
        memoria[1387] = (byte) 0xFF;
        memoria[1388] = (byte) 0xFF;
        memoria[1389] = (byte) 0x58;
        memoria[1390] = (byte) 0xBA;
        memoria[1391] = (byte) 0x00;
        memoria[1392] = (byte) 0x00;
        memoria[1393] = (byte) 0x00;
        memoria[1394] = (byte) 0x00;
        memoria[1395] = (byte) 0xBB;
        memoria[1396] = (byte) 0x0A;
        memoria[1397] = (byte) 0x00;
        memoria[1398] = (byte) 0x00;
        memoria[1399] = (byte) 0x00;
        memoria[1400] = (byte) 0xF7;
        memoria[1401] = (byte) 0xFB;
        memoria[1402] = (byte) 0x52;
        memoria[1403] = (byte) 0xE8;
        memoria[1404] = (byte) 0x80;
        memoria[1405] = (byte) 0xFE;
        memoria[1406] = (byte) 0xFF;
        memoria[1407] = (byte) 0xFF;
        memoria[1408] = (byte) 0x58;
        memoria[1409] = (byte) 0xE8;
        memoria[1410] = (byte) 0x7A;
        memoria[1411] = (byte) 0xFE;
        memoria[1412] = (byte) 0xFF;
        memoria[1413] = (byte) 0xFF;
        memoria[1414] = (byte) 0xC3;
        memoria[1415] = (byte) 0x00;
        memoria[1416] = (byte) 0xFF;
        memoria[1417] = (byte) 0x15;
        memoria[1418] = (byte) 0x00;
        memoria[1419] = (byte) 0x10;
        memoria[1420] = (byte) 0x40;
        memoria[1421] = (byte) 0x00;
        memoria[1422] = (byte) 0x00;
        memoria[1423] = (byte) 0x00;
        memoria[1424] = (byte) 0xB9;
        memoria[1425] = (byte) 0x00;
        memoria[1426] = (byte) 0x00;
        memoria[1427] = (byte) 0x00;
        memoria[1428] = (byte) 0x00;
        memoria[1429] = (byte) 0xB3;
        memoria[1430] = (byte) 0x03;
        memoria[1431] = (byte) 0x51;
        memoria[1432] = (byte) 0x53;
        memoria[1433] = (byte) 0xE8;
        memoria[1434] = (byte) 0xA2;
        memoria[1435] = (byte) 0xFD;
        memoria[1436] = (byte) 0xFF;
        memoria[1437] = (byte) 0xFF;
        memoria[1438] = (byte) 0x5B;
        memoria[1439] = (byte) 0x59;
        memoria[1440] = (byte) 0x3C;
        memoria[1441] = (byte) 0x0D;
        memoria[1442] = (byte) 0x0F;
        memoria[1443] = (byte) 0x84;
        memoria[1444] = (byte) 0x34;
        memoria[1445] = (byte) 0x01;
        memoria[1446] = (byte) 0x00;
        memoria[1447] = (byte) 0x00;
        memoria[1448] = (byte) 0x3C;
        memoria[1449] = (byte) 0x08;
        memoria[1450] = (byte) 0x0F;
        memoria[1451] = (byte) 0x84;
        memoria[1452] = (byte) 0x94;
        memoria[1453] = (byte) 0x00;
        memoria[1454] = (byte) 0x00;
        memoria[1455] = (byte) 0x00;
        memoria[1456] = (byte) 0x3C;
        memoria[1457] = (byte) 0x2D;
        memoria[1458] = (byte) 0x0F;
        memoria[1459] = (byte) 0x84;
        memoria[1460] = (byte) 0x09;
        memoria[1461] = (byte) 0x01;
        memoria[1462] = (byte) 0x00;
        memoria[1463] = (byte) 0x00;
        memoria[1464] = (byte) 0x3C;
        memoria[1465] = (byte) 0x30;
        memoria[1466] = (byte) 0x7C;
        memoria[1467] = (byte) 0xDB;
        memoria[1468] = (byte) 0x3C;
        memoria[1469] = (byte) 0x39;
        memoria[1470] = (byte) 0x7F;
        memoria[1471] = (byte) 0xD7;
        memoria[1472] = (byte) 0x2C;
        memoria[1473] = (byte) 0x30;
        memoria[1474] = (byte) 0x80;
        memoria[1475] = (byte) 0xFB;
        memoria[1476] = (byte) 0x00;
        memoria[1477] = (byte) 0x74;
        memoria[1478] = (byte) 0xD0;
        memoria[1479] = (byte) 0x80;
        memoria[1480] = (byte) 0xFB;
        memoria[1481] = (byte) 0x02;
        memoria[1482] = (byte) 0x75;
        memoria[1483] = (byte) 0x0C;
        memoria[1484] = (byte) 0x81;
        memoria[1485] = (byte) 0xF9;
        memoria[1486] = (byte) 0x00;
        memoria[1487] = (byte) 0x00;
        memoria[1488] = (byte) 0x00;
        memoria[1489] = (byte) 0x00;
        memoria[1490] = (byte) 0x75;
        memoria[1491] = (byte) 0x04;
        memoria[1492] = (byte) 0x3C;
        memoria[1493] = (byte) 0x00;
        memoria[1494] = (byte) 0x74;
        memoria[1495] = (byte) 0xBF;
        memoria[1496] = (byte) 0x80;
        memoria[1497] = (byte) 0xFB;
        memoria[1498] = (byte) 0x03;
        memoria[1499] = (byte) 0x75;
        memoria[1500] = (byte) 0x0A;
        memoria[1501] = (byte) 0x3C;
        memoria[1502] = (byte) 0x00;
        memoria[1503] = (byte) 0x75;
        memoria[1504] = (byte) 0x04;
        memoria[1505] = (byte) 0xB3;
        memoria[1506] = (byte) 0x00;
        memoria[1507] = (byte) 0xEB;
        memoria[1508] = (byte) 0x02;
        memoria[1509] = (byte) 0xB3;
        memoria[1510] = (byte) 0x01;
        memoria[1511] = (byte) 0x81;
        memoria[1512] = (byte) 0xF9;
        memoria[1513] = (byte) 0xCC;
        memoria[1514] = (byte) 0xCC;
        memoria[1515] = (byte) 0xCC;
        memoria[1516] = (byte) 0x0C;
        memoria[1517] = (byte) 0x7F;
        memoria[1518] = (byte) 0xA8;
        memoria[1519] = (byte) 0x81;
        memoria[1520] = (byte) 0xF9;
        memoria[1521] = (byte) 0x34;
        memoria[1522] = (byte) 0x33;
        memoria[1523] = (byte) 0x33;
        memoria[1524] = (byte) 0xF3;
        memoria[1525] = (byte) 0x7C;
        memoria[1526] = (byte) 0xA0;
        memoria[1527] = (byte) 0x88;
        memoria[1528] = (byte) 0xC7;
        memoria[1529] = (byte) 0xB8;
        memoria[1530] = (byte) 0x0A;
        memoria[1531] = (byte) 0x00;
        memoria[1532] = (byte) 0x00;
        memoria[1533] = (byte) 0x00;
        memoria[1534] = (byte) 0xF7;
        memoria[1535] = (byte) 0xE9;
        memoria[1536] = (byte) 0x3D;
        memoria[1537] = (byte) 0x08;
        memoria[1538] = (byte) 0x00;
        memoria[1539] = (byte) 0x00;
        memoria[1540] = (byte) 0x80;
        memoria[1541] = (byte) 0x74;
        memoria[1542] = (byte) 0x11;
        memoria[1543] = (byte) 0x3D;
        memoria[1544] = (byte) 0xF8;
        memoria[1545] = (byte) 0xFF;
        memoria[1546] = (byte) 0xFF;
        memoria[1547] = (byte) 0x7F;
        memoria[1548] = (byte) 0x75;
        memoria[1549] = (byte) 0x13;
        memoria[1550] = (byte) 0x80;
        memoria[1551] = (byte) 0xFF;
        memoria[1552] = (byte) 0x07;
        memoria[1553] = (byte) 0x7E;
        memoria[1554] = (byte) 0x0E;
        memoria[1555] = (byte) 0xE9;
        memoria[1556] = (byte) 0x7F;
        memoria[1557] = (byte) 0xFF;
        memoria[1558] = (byte) 0xFF;
        memoria[1559] = (byte) 0xFF;
        memoria[1560] = (byte) 0x80;
        memoria[1561] = (byte) 0xFF;
        memoria[1562] = (byte) 0x08;
        memoria[1563] = (byte) 0x0F;
        memoria[1564] = (byte) 0x8F;
        memoria[1565] = (byte) 0x76;
        memoria[1566] = (byte) 0xFF;
        memoria[1567] = (byte) 0xFF;
        memoria[1568] = (byte) 0xFF;
        memoria[1569] = (byte) 0xB9;
        memoria[1570] = (byte) 0x00;
        memoria[1571] = (byte) 0x00;
        memoria[1572] = (byte) 0x00;
        memoria[1573] = (byte) 0x00;
        memoria[1574] = (byte) 0x88;
        memoria[1575] = (byte) 0xF9;
        memoria[1576] = (byte) 0x80;
        memoria[1577] = (byte) 0xFB;
        memoria[1578] = (byte) 0x02;
        memoria[1579] = (byte) 0x74;
        memoria[1580] = (byte) 0x04;
        memoria[1581] = (byte) 0x01;
        memoria[1582] = (byte) 0xC1;
        memoria[1583] = (byte) 0xEB;
        memoria[1584] = (byte) 0x03;
        memoria[1585] = (byte) 0x29;
        memoria[1586] = (byte) 0xC8;
        memoria[1587] = (byte) 0x91;
        memoria[1588] = (byte) 0x88;
        memoria[1589] = (byte) 0xF8;
        memoria[1590] = (byte) 0x51;
        memoria[1591] = (byte) 0x53;
        memoria[1592] = (byte) 0xE8;
        memoria[1593] = (byte) 0xC3;
        memoria[1594] = (byte) 0xFD;
        memoria[1595] = (byte) 0xFF;
        memoria[1596] = (byte) 0xFF;
        memoria[1597] = (byte) 0x5B;
        memoria[1598] = (byte) 0x59;
        memoria[1599] = (byte) 0xE9;
        memoria[1600] = (byte) 0x53;
        memoria[1601] = (byte) 0xFF;
        memoria[1602] = (byte) 0xFF;
        memoria[1603] = (byte) 0xFF;
        memoria[1604] = (byte) 0x80;
        memoria[1605] = (byte) 0xFB;
        memoria[1606] = (byte) 0x03;
        memoria[1607] = (byte) 0x0F;
        memoria[1608] = (byte) 0x84;
        memoria[1609] = (byte) 0x4A;
        memoria[1610] = (byte) 0xFF;
        memoria[1611] = (byte) 0xFF;
        memoria[1612] = (byte) 0xFF;
        memoria[1613] = (byte) 0x51;
        memoria[1614] = (byte) 0x53;
        memoria[1615] = (byte) 0xB0;
        memoria[1616] = (byte) 0x08;
        memoria[1617] = (byte) 0xE8;
        memoria[1618] = (byte) 0x7A;
        memoria[1619] = (byte) 0xFC;
        memoria[1620] = (byte) 0xFF;
        memoria[1621] = (byte) 0xFF;
        memoria[1622] = (byte) 0xB0;
        memoria[1623] = (byte) 0x20;
        memoria[1624] = (byte) 0xE8;
        memoria[1625] = (byte) 0x73;
        memoria[1626] = (byte) 0xFC;
        memoria[1627] = (byte) 0xFF;
        memoria[1628] = (byte) 0xFF;
        memoria[1629] = (byte) 0xB0;
        memoria[1630] = (byte) 0x08;
        memoria[1631] = (byte) 0xE8;
        memoria[1632] = (byte) 0x6C;
        memoria[1633] = (byte) 0xFC;
        memoria[1634] = (byte) 0xFF;
        memoria[1635] = (byte) 0xFF;
        memoria[1636] = (byte) 0x5B;
        memoria[1637] = (byte) 0x59;
        memoria[1638] = (byte) 0x80;
        memoria[1639] = (byte) 0xFB;
        memoria[1640] = (byte) 0x00;
        memoria[1641] = (byte) 0x75;
        memoria[1642] = (byte) 0x07;
        memoria[1643] = (byte) 0xB3;
        memoria[1644] = (byte) 0x03;
        memoria[1645] = (byte) 0xE9;
        memoria[1646] = (byte) 0x25;
        memoria[1647] = (byte) 0xFF;
        memoria[1648] = (byte) 0xFF;
        memoria[1649] = (byte) 0xFF;
        memoria[1650] = (byte) 0x80;
        memoria[1651] = (byte) 0xFB;
        memoria[1652] = (byte) 0x02;
        memoria[1653] = (byte) 0x75;
        memoria[1654] = (byte) 0x0F;
        memoria[1655] = (byte) 0x81;
        memoria[1656] = (byte) 0xF9;
        memoria[1657] = (byte) 0x00;
        memoria[1658] = (byte) 0x00;
        memoria[1659] = (byte) 0x00;
        memoria[1660] = (byte) 0x00;
        memoria[1661] = (byte) 0x75;
        memoria[1662] = (byte) 0x07;
        memoria[1663] = (byte) 0xB3;
        memoria[1664] = (byte) 0x03;
        memoria[1665] = (byte) 0xE9;
        memoria[1666] = (byte) 0x11;
        memoria[1667] = (byte) 0xFF;
        memoria[1668] = (byte) 0xFF;
        memoria[1669] = (byte) 0xFF;
        memoria[1670] = (byte) 0x89;
        memoria[1671] = (byte) 0xC8;
        memoria[1672] = (byte) 0xB9;
        memoria[1673] = (byte) 0x0A;
        memoria[1674] = (byte) 0x00;
        memoria[1675] = (byte) 0x00;
        memoria[1676] = (byte) 0x00;
        memoria[1677] = (byte) 0xBA;
        memoria[1678] = (byte) 0x00;
        memoria[1679] = (byte) 0x00;
        memoria[1680] = (byte) 0x00;
        memoria[1681] = (byte) 0x00;
        memoria[1682] = (byte) 0x3D;
        memoria[1683] = (byte) 0x00;
        memoria[1684] = (byte) 0x00;
        memoria[1685] = (byte) 0x00;
        memoria[1686] = (byte) 0x00;
        memoria[1687] = (byte) 0x7D;
        memoria[1688] = (byte) 0x08;
        memoria[1689] = (byte) 0xF7;
        memoria[1690] = (byte) 0xD8;
        memoria[1691] = (byte) 0xF7;
        memoria[1692] = (byte) 0xF9;
        memoria[1693] = (byte) 0xF7;
        memoria[1694] = (byte) 0xD8;
        memoria[1695] = (byte) 0xEB;
        memoria[1696] = (byte) 0x02;
        memoria[1697] = (byte) 0xF7;
        memoria[1698] = (byte) 0xF9;
        memoria[1699] = (byte) 0x89;
        memoria[1700] = (byte) 0xC1;
        memoria[1701] = (byte) 0x81;
        memoria[1702] = (byte) 0xF9;
        memoria[1703] = (byte) 0x00;
        memoria[1704] = (byte) 0x00;
        memoria[1705] = (byte) 0x00;
        memoria[1706] = (byte) 0x00;
        memoria[1707] = (byte) 0x0F;
        memoria[1708] = (byte) 0x85;
        memoria[1709] = (byte) 0xE6;
        memoria[1710] = (byte) 0xFE;
        memoria[1711] = (byte) 0xFF;
        memoria[1712] = (byte) 0xFF;
        memoria[1713] = (byte) 0x80;
        memoria[1714] = (byte) 0xFB;
        memoria[1715] = (byte) 0x02;
        memoria[1716] = (byte) 0x0F;
        memoria[1717] = (byte) 0x84;
        memoria[1718] = (byte) 0xDD;
        memoria[1719] = (byte) 0xFE;
        memoria[1720] = (byte) 0xFF;
        memoria[1721] = (byte) 0xFF;
        memoria[1722] = (byte) 0xB3;
        memoria[1723] = (byte) 0x03;
        memoria[1724] = (byte) 0xE9;
        memoria[1725] = (byte) 0xD6;
        memoria[1726] = (byte) 0xFE;
        memoria[1727] = (byte) 0xFF;
        memoria[1728] = (byte) 0xFF;
        memoria[1729] = (byte) 0x80;
        memoria[1730] = (byte) 0xFB;
        memoria[1731] = (byte) 0x03;
        memoria[1732] = (byte) 0x0F;
        memoria[1733] = (byte) 0x85;
        memoria[1734] = (byte) 0xCD;
        memoria[1735] = (byte) 0xFE;
        memoria[1736] = (byte) 0xFF;
        memoria[1737] = (byte) 0xFF;
        memoria[1738] = (byte) 0xB0;
        memoria[1739] = (byte) 0x2D;
        memoria[1740] = (byte) 0x51;
        memoria[1741] = (byte) 0x53;
        memoria[1742] = (byte) 0xE8;
        memoria[1743] = (byte) 0xFD;
        memoria[1744] = (byte) 0xFB;
        memoria[1745] = (byte) 0xFF;
        memoria[1746] = (byte) 0xFF;
        memoria[1747] = (byte) 0x5B;
        memoria[1748] = (byte) 0x59;
        memoria[1749] = (byte) 0xB3;
        memoria[1750] = (byte) 0x02;
        memoria[1751] = (byte) 0xE9;
        memoria[1752] = (byte) 0xBB;
        memoria[1753] = (byte) 0xFE;
        memoria[1754] = (byte) 0xFF;
        memoria[1755] = (byte) 0xFF;
        memoria[1756] = (byte) 0x80;
        memoria[1757] = (byte) 0xFB;
        memoria[1758] = (byte) 0x03;
        memoria[1759] = (byte) 0x0F;
        memoria[1760] = (byte) 0x84;
        memoria[1761] = (byte) 0xB2;
        memoria[1762] = (byte) 0xFE;
        memoria[1763] = (byte) 0xFF;
        memoria[1764] = (byte) 0xFF;
        memoria[1765] = (byte) 0x80;
        memoria[1766] = (byte) 0xFB;
        memoria[1767] = (byte) 0x02;
        memoria[1768] = (byte) 0x75;
        memoria[1769] = (byte) 0x0C;
        memoria[1770] = (byte) 0x81;
        memoria[1771] = (byte) 0xF9;
        memoria[1772] = (byte) 0x00;
        memoria[1773] = (byte) 0x00;
        memoria[1774] = (byte) 0x00;
        memoria[1775] = (byte) 0x00;
        memoria[1776] = (byte) 0x0F;
        memoria[1777] = (byte) 0x84;
        memoria[1778] = (byte) 0xA1;
        memoria[1779] = (byte) 0xFE;
        memoria[1780] = (byte) 0xFF;
        memoria[1781] = (byte) 0xFF;
        memoria[1782] = (byte) 0x51;
        memoria[1783] = (byte) 0xE8;
        memoria[1784] = (byte) 0x14;
        memoria[1785] = (byte) 0xFD;
        memoria[1786] = (byte) 0xFF;
        memoria[1787] = (byte) 0xFF;
        memoria[1788] = (byte) 0x59;
        memoria[1789] = (byte) 0x89;
        memoria[1790] = (byte) 0xC8;
        memoria[1791] = (byte) 0xC3;
        this.proxLibre = 1792;
        try 
        {
            bwe = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nombre)));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ArchivoEjecutable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getProxLibre() 
    {
        return proxLibre;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void escribirByte(byte b) 
    {
        try 
        {
            bwe.writeByte(b);
        } 
        catch (IOException ex) 
        {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
    }

    private void cerrar() 
    {
        try 
        {
            bwe.close();
        } 
        catch (IOException ex) 
        {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
    }
    
    public void generarEncabezado()
    { 
       
        for (int i = 0; i < 1792; i++) 
        {
            escribirByte(memoria[i]);
        }
        
        
         
    }
    
    public void cargarByteEnMemoria (int b)
    {
        memoria[proxLibre] = (byte) b;
        proxLibre++;
    }
    
    public void cargarEnteroEnMemoria (long e)
    {
        // REVISAR PARA NUMEROS NEGATIVOS !
        
        if(e>=0){
        memoria[proxLibre] = (byte) b1(e);
        proxLibre++;
        memoria[proxLibre] = (byte) b2(e);
        proxLibre++;
        memoria[proxLibre] = (byte) b3(e);
        proxLibre++;
        memoria[proxLibre] = (byte) b4(e);
        proxLibre++;
        }else{
            
             e = 0xFFFFFFFFL + e + 1;
             
            memoria[proxLibre] = (byte) b1(e);
            proxLibre++;
            memoria[proxLibre] = (byte) b2(e);
            proxLibre++;
            memoria[proxLibre] = (byte) b3(e);
            proxLibre++;
            memoria[proxLibre] = (byte) b4(e);
            proxLibre++;           
            
        }
    }
    
    public void cargarByteEnMemoria (int b, int d)
    {
        memoria[d]=(byte)b;
    }
    
    public void cargarEnteroEnMemoria (long e, int d)
    {
        
        if(e>=0){
            memoria[d] = (byte) b1(e);
            d++;
            memoria[d] = (byte) b2(e);
            d++;
            memoria[d] = (byte) b3(e);
            d++;
            memoria[d] = (byte) b4(e);
        }else{
            e = 0xFFFFFFFFL + e + 1;
            
            memoria[d] = (byte) b1(e);
            d++;
            memoria[d] = (byte) b2(e);
            d++;
            memoria[d] = (byte) b3(e);
            d++;
            memoria[d] = (byte) b4(e);
            
        }
    }
    
    public void finalizarArchivo()
    {
        for (int i = 1792; i < proxLibre; i++) 
        {
            escribirByte(memoria[i]);
        }
        
        cerrar();
    }
    private long b1(long numero) {
        return numero % 256;
    }

    private long b2(long numero) {
        return (numero / 256) % 256;
    }

    private long b3(long numero) {
        return ((numero / 256) / 256) % 256;
    }

    private long b4(long numero) {
        return (((numero / 256) / 256) / 256) % 256;
    }
    
    public int componer (int n)
    {
        return (memoria[n] + 256*memoria[n+1] + 256*256*memoria[n+2] + 256*256*256*memoria[n+3]);
    }
}
  