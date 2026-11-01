import java.util.Arrays;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     public static String verify(String traversal1, String traversal2, String traversal3){
          int[][] traversals = new int[3][];
          try {
               traversals[0] = parseTraversal(traversal1);
               traversals[1] = parseTraversal(traversal2);
               traversals[2] = parseTraversal(traversal3);
          } catch (IllegalArgumentException exception) {
               return "Invalid traversals";
          }

          int[] sorted = traversals[0].clone();
          Arrays.sort(sorted);
          for (int i = 1; i < sorted.length; i++) {
               if (sorted[i] == sorted[i - 1]) {
                    return "Invalid traversals";
               }
          }
          for (int i = 1; i < 3; i++) {
               int[] other = traversals[i].clone();
               Arrays.sort(other);
               if (!Arrays.equals(sorted, other)) {
                    return "Invalid traversals";
               }
          }

          for (int inorder = 0; inorder < 3; inorder++) {
               if (!Arrays.equals(traversals[inorder], sorted)) {
                    continue;
               }
               for (int preorder = 0; preorder < 3; preorder++) {
                    if (preorder == inorder) {
                         continue;
                    }
                    int postorder = 3 - inorder - preorder;
                    if (matchesTree(sorted, traversals[preorder], traversals[postorder], 0, sorted.length - 1, 0, 0)) {
                         String[] orders = new String[3];
                         orders[inorder] = "Inorder";
                         orders[preorder] = "Preorder";
                         orders[postorder] = "Postorder";
                         return "Traversal 1 - " + orders[0] + ", Traversal 2 - "
                                   + orders[1] + ", Traversal 3 - " + orders[2];
                    }
               }
          }
          return "Invalid traversals";
     }

     private static int[] parseTraversal(String traversal) {
          if (traversal == null || traversal.trim().isEmpty()) {
               throw new IllegalArgumentException("Traversal must contain nodes");
          }
          String[] nodes = traversal.split("-", -1);
          int[] values = new int[nodes.length];
          for (int i = 0; i < nodes.length; i++) {
               values[i] = Integer.parseInt(nodes[i].trim());
          }
          return values;
     }

     private static boolean matchesTree(int[] inorder, int[] preorder, int[] postorder, int low, int high, int preStart, int postStart) {
          if (low > high) {
               return true;
          }
          
          int size = high - low + 1;
          int root = preorder[preStart];
          if (root != postorder[postStart + size - 1]) {
               return false;
          }
          int rootIndex = Arrays.binarySearch(inorder, low, high + 1, root);
          if (rootIndex < 0) {
               return false;
          }
          int leftSize = rootIndex - low;
          return matchesTree(inorder, preorder, postorder, low, rootIndex - 1, preStart + 1, postStart)
               && matchesTree(inorder, preorder, postorder, rootIndex + 1, high, preStart + leftSize + 1, postStart + leftSize);
     }
}
