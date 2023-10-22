package MinMax;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import org.json.JSONException;

public class MinMax {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String option;
        ActuallTreeClass tree = null;
        OptimalTreeClass tree2 = null;
        String content = " ";
        int pIsCalled = 0;

        do {
            System.out.println("\n-i <filename>   :  insert tree from file");
            System.out.println("-j [<filename>] :  print tree in the specified filename using JSON format");
            System.out.println("-d [<filename>] :  print tree in the specified filename using DOT format");
            System.out.println("-c              :  calculate tree using min-max algorithm");
            System.out.println("-p              :  calculate tree using min-max and alpha-beta pruning optimization");
            System.out.println("-q              :  quit this program");
            System.out.print("\n$> ");

            option = input.nextLine().trim();
            String[] tokens = option.split("\\s+");
            
            switch (tokens[0]) {
                case "-i":
                    System.out.print("\n");
                    try {
                        File file = new File(tokens[1]);
                        if (!file.exists()) {
                            throw new FileNotFoundException("Unable to find " + "'" + tokens[1] + "'");
                        }
                        if (!file.canRead()) {
                           throw new SecurityException("Unable to open " + "'" + tokens[1] + "'");
                        }
                        Scanner fileReader = new Scanner(file);
                        StringBuilder contentBuilder = new StringBuilder();
                        while (fileReader.hasNextLine()) {
                            contentBuilder.append(fileReader.nextLine());
                        }
                        fileReader.close();
                        content = contentBuilder.toString();
                        try {
                            tree = new ActuallTreeClass(content);
                        } catch (JSONException e) {
                            System.out.print("Invalid format");
                            System.out.println("\n");
                            System.out.print("\n");
                            break;
                        }   
                        System.out.print("OK");
                    } catch (FileNotFoundException e) {
                        System.out.print("Unable to find " + "'" + tokens[1] + "'");
                    } catch (SecurityException e) {
                        System.out.print("Unable to open " + "'" + tokens[1] + "'");
                    }

                    System.out.println("\n");
                    System.out.print("\n");
                    break;
                case "-p":
                    System.out.print("\n");
                    if(tree != null) {
                        pIsCalled = 1;
                        tree2 = new OptimalTreeClass(content);
                        tree2.minMax();
                        int pruned = tree2.prunedNodes();
                        int total = tree2.totalNodes();
                        System.out.print("[" + total + "," + pruned + "] ");
                        ArrayList<Integer> numbers2 = new ArrayList<Integer>();
                        numbers2 = tree2.optimalPath();
                        if(numbers2.size() == 0) {
                            //do nothing
                        }
                        else if(numbers2.size() == 1) {
                            System.out.print(numbers2.get(0));
                        }
                        else {
                            System.out.print(numbers2.get(0));
                            for (int i = 1; i < numbers2.size(); i++) {
                                System.out.print(", " + numbers2.get(i));
                            }
                        }
                    System.out.println("\n");
                    System.out.print("\n");
                    }
                    break;
                case "-c":
                    System.out.print("\n");
                    if(tree != null) {
                        tree.minMax();
                        ArrayList<Integer> numbers2 = new ArrayList<Integer>();
                        numbers2 = tree.optimalPath();
                        if(numbers2.size() == 0) {
                            //do nothing
                        }
                        else if(numbers2.size() == 1) {
                            System.out.print(numbers2.get(0));
                        }
                        else {
                            System.out.print(numbers2.get(0));
                            for (int i = 1; i < numbers2.size(); i++) {
                                System.out.print(", " + numbers2.get(i));
                            }
                        }
                    }
                    System.out.println("\n");
                    System.out.print("\n");
                    break;
                case "-j":
                    System.out.print("\n");
                    if(tree2 != null && pIsCalled == 1) {
                       try {
                            if(tokens.length > 1) {
                                File file2 = new File(tokens[1]);
                                if (file2.exists()) {
                                     System.out.print("File "  + "'" + tokens[1] + "'" + " already exists");
                                }
                                else {
                                    tree2.toFile(file2);
                                    System.out.print("OK");
                                }
                            }
                            else {
                                String json = tree2.toString();
                                System.out.println(json);
                            }
                        } catch (IOException e) {
                            System.out.print("Unable to write " + "'" + tokens[1] + "'");
                        }
                    }
                    else if(tree != null) {
                        try {
                            if(tokens.length > 1) {
                                File file2 = new File(tokens[1]);
                                if (file2.exists()) {
                                     System.out.print("File "  + "'" + tokens[1] + "'" + " already exists");
                                }
                                else {
                                    tree.toFile(file2);
                                    System.out.print("OK");
                                }
                            }
                            else {
                                String json = tree.toString();
                                System.out.println(json);
                            }
                        } catch (IOException e) {
                            System.out.print("Unable to write " + "'" + tokens[1] + "'");
                        }
                    }
                    System.out.println("\n");
                    System.out.print("\n");
                    break;
                case "-d":
                    System.out.print("\n");
                    if(tree2 != null && pIsCalled == 1) {
                       try {
                            File file2 = new File(tokens[1]);
                            if (file2.exists()) {
                                 System.out.print("File "  + "'" + tokens[1] + "'" + " already exists");
                            }
                            else {
                                tree2.toDotFile(file2);
                                System.out.print("OK");
                            }
                        } catch (IOException e) {
                            System.out.print("Unable to write " + "'" + tokens[1] + "'");
                        } 
                    }
                    else if(tree != null) {
                        try {
                            File file3 = new File(tokens[1]);
                            if (file3.exists()) {
                                 System.out.print("File "  + "'" + tokens[1] + "'" + " already exists");
                            }
                            else {
                                tree.toDotFile(file3);
                                System.out.print("OK");
                            }
                        } catch (IOException e) {
                            System.out.print("Unable to write " + "'" + tokens[1] + "'");
                        }
                    }
                    System.out.println("\n");
                    System.out.print("\n");
                    break;
                case "-q":
                    break;
                default:
            }
        }while (!option.equals("-q"));
        input.close();
    }
}