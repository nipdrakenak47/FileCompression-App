-> Hello ! This project is about file compression(Text file) using Huffman code. 

-> As File compression means, the encoded file should have less size than actual file, So I have developed this Graphical User Interface based Java Application.
For GUI, Java Swing has been used here and Java I/O used for File encoding and decoding from host.



-> Here is the demonstration, how the appliaction work :

1) First after starting application It will show GUI to select ".txt" file for Compression.

  ![Screenshot 2025-04-20 at 2 35 07 PM](https://github.com/user-attachments/assets/e571c5ab-6f2b-4519-b174-6deadcf0d433)


2) Now click on "Choose file" button to select input file for compression. I am selecting "input.txt" file.

  ![Screenshot 2025-04-20 at 2 39 26 PM](https://github.com/user-attachments/assets/a282ddbd-4c06-4959-95da-232c031216bc)

  ![Screenshot 2025-04-20 at 2 40 26 PM](https://github.com/user-attachments/assets/f38217d9-1c2b-4fbf-9a0d-7199abe41ad2)


3) After that click on "Encode" button, it will start process of compressing file and show dialog like below. 

   ![Screenshot 2025-04-20 at 2 42 38 PM](https://github.com/user-attachments/assets/7287f14e-3cb4-44fc-8863-7dcf73a4bc39)

   
   After it complete successfully it will show another dialog box also :
   
   ![Screenshot 2025-04-20 at 2 43 18 PM](https://github.com/user-attachments/assets/b204861c-8443-4195-b9d6-eb2ed4d13382)


4) After processing completes, there will be a file generated in same folder where input file is present. output file name will be "output_huff.txt".
    (If another file is already present with same name then that case is handleed by code, and select the unused file_name)
   Below we can see, there is file generated "output_huff.txt" having size of 1.6 MB , that was reduced from 2.6 MB of "input.txt" file.

    ![Screenshot 2025-04-20 at 3 24 08 PM 1](https://github.com/user-attachments/assets/8154dba8-de8d-4ab1-a4be-2c5b57df71fe)








-> Now If you want to restore original file data into new file then, You can choose file and clik "Decode" button on GUI, it will generate new file in same Directory by name of "decoded_result.txt".
Here is the snapshot of both, Original file and Decoded file. (Unique file_name case is covered)

![Screenshot 2025-04-20 at 3 38 58 PM](https://github.com/user-attachments/assets/2248e45d-3266-4d9b-bfa0-18fb521b877a)

![Screenshot 2025-04-20 at 3 40 51 PM](https://github.com/user-attachments/assets/f0898d17-5280-4129-9a74-fff03690fd3d)

