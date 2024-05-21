public class KnapsackGreedy {
    private int n; 
    private int W; 
    private int[] values;
    private int[] weights;
    //part optimization 

  
    private static String[] splitString(String input) {
        int spaceCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') spaceCount++;
        }
        //get numbers of string 
        String[] parts = new String[spaceCount + 1];
        int lastSpaceIndex = -1, partIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                //spaze = previous part store in parts
                parts[partIndex++] = input.substring(lastSpaceIndex + 1, i);
                lastSpaceIndex = i;
            }
        }
        parts[partIndex] = input.substring(lastSpaceIndex + 1);
        return parts;
    }

    public KnapsackGreedy(String input) {
        String[] parts = splitString(input);
        //string divide in to different parts, store in the parts
        n = Integer.parseInt(parts[0]);
        //number size of item
        W = Integer.parseInt(parts[1]);
        //weight of item
        values = new int[n];
        weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(parts[2 + 2 * i]);
            weights[i] = Integer.parseInt(parts[3 + 2 * i]);
        }
    }

    // Custom insertion sort for Item objects based on their ratio
    private static void sortItems(Item[] items) {
        for (int i = 1; i < items.length; i++) {
            Item key = items[i];
            int j = i - 1;
            //sort items based on ratio of value and weight from high to low

            while (j >= 0 && items[j].ratio < key.ratio) {
                items[j + 1] = items[j];
                j = j - 1;
            }
            items[j + 1] = key;
        }
    }

    public static String fractional(String input) {
        KnapsackGreedy kg = new KnapsackGreedy(input);
        Item[] items = new Item[kg.n];
        //item = one item (value, weight, ratio, index)
        for (int i = 0; i < kg.n; i++) {
            items[i] = new Item(kg.values[i], kg.weights[i], i);
        }
        sortItems(items); 
        //order ratio from high to low

        double totalValue = 0.0;
        double totalWeight = 0.0;
        double[] fractions = new double[kg.n];

        for (Item item : items) {
            if (totalWeight + item.weight <= kg.W) {
                //not exceed weight
                totalWeight += item.weight;
                totalValue += item.value;
                fractions[item.index] = 1.0;
            } else {
                //calculate exceed parts
                double remain = kg.W - totalWeight;
                double fraction = remain / item.weight;
                totalValue += item.value * fraction;
                totalWeight += item.weight * fraction;
                fractions[item.index] = fraction;
                break;
            }
        }

        //return formatOutput(fractions, totalValue, totalWeight);

        StringBuilder sb = new StringBuilder();
        for (double fraction : fractions) {
            if (fraction == (long) fraction) {
                
                sb.append(String.format("%.1f, ", fraction));
            } else {
                
                sb.append(String.format("%s, ", fraction));
            }
        }
        
        
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(String.format(", total value = %s, total weight = %.1f", totalValue, (double)totalWeight));
        return sb.toString();
    }

    
    private static class Item {
        int value;
        int weight;
        double ratio;
        int index;

        public Item(int value, int weight, int index) {
            this.value = value;
            this.weight = weight;
            this.ratio = (double) value / weight;
            this.index = index;
        }
    }

    public static String greedy01(String input) {
        KnapsackGreedy kg = new KnapsackGreedy(input);
        Item[] items = new Item[kg.n];
        for (int i = 0; i < kg.n; i++) {
            items[i] = new Item(kg.values[i], kg.weights[i], i);
        }
        
        sortItems(items);
    
        int totalValue = 0;
        int totalWeight = 0;
        int[] selected = new int[kg.n]; 
    
        for (Item item : items) {
            if (totalWeight + item.weight <= kg.W) {
                totalWeight += item.weight;
                totalValue += item.value;
                selected[item.index] = 1; 
            }
        }//exceed parts not put in the bag

        StringBuilder sb = new StringBuilder();
        for (int isSelected : selected) {
            
            sb.append(String.format("%.1f, ", (double)isSelected)); 
        }
       
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(String.format(", total value = %.1f, total weight = %.1f", (double)totalValue, (double)totalWeight));
        return sb.toString();

      
    }
   
}
