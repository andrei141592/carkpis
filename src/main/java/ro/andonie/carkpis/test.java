package ro.andonie.carkpis;

import de.vandermeer.asciitable.AsciiTable;

public class test {
    public static void main(String[] args) {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Column 1", "Column 2", "Column 3");
        at.addRule();
        at.addRow("Value 1", "Value 2222222222222", "Value 3");
        at.addRule();
        at.addRow("Value 4", "Value 5", "Value 6");
        at.addRule();

        String rend = at.render();
        System.out.println(rend);
    }
}
