b1tparis0n
==========

binary byte wise file comparing command line tool and patch creation utility

usage: <b1tparis0n> "original_file" "mutated_file" "result_directory"

output will be something like ( here comparing two pdf files... ):

--------------------------------------------------------------------------------
[b1p] - B1tpARiS0n v1.0 - mitp0sh @ 2014
--------------------------------------------------------------------------------

Comparing files now...

  DiffMap ->

Offset: 0000004094, origByte = 0x94 ( 10010100 ), muteByte = 0xe3 ( 11100011 )
Offset: 0000012006, origByte = 0x48 ( 01001000 ), muteByte = 0xaa ( 10101010 )
Offset: 0000014547, origByte = 0xe7 ( 11100111 ), muteByte = 0x4e ( 01001110 )
Offset: 0000020175, origByte = 0x91 ( 10010001 ), muteByte = 0x38 ( 00111000 )

Number of BytePatches: 250

Create split patch files...

Writing file... /Users/xxx/Desktop/result_dir/0000004094.pdf
Writing file... /Users/xxx/Desktop/result_dir/0000012006.pdf
Writing file... /Users/xxx/Desktop/result_dir/0000014547.pdf
Writing file... /Users/xxx/Desktop/result_dir/0000020175.pdf

Finished!

--------------------------------------------------------------------------------

For each different byte a new file is created which reflects only a one byte 
patch. can be very handy for exploit analysis and similiar tasks.

I use it on a regular basis...

mitp0sh
