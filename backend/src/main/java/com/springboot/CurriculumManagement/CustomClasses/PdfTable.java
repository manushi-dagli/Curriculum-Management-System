package com.springboot.CurriculumManagement.CustomClasses;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PdfTable extends Document {

    private PdfPTable table;

    public PdfTable() throws DocumentException {
        super();
        table = new PdfPTable(9); // 9 columns
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
    }

    public void addCell(String cellValue) {
        PdfPCell cell = new PdfPCell(new Phrase(cellValue));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setBorder(Rectangle.TOP);
//        cell.setBorder(Rectangle.BOTTOM);
//        cell.setBorder(Rectangle.RIGHT);
//        cell.setBorder(Rectangle.LEFT);// set border for the cell
        cell.setPadding(5f); // set padding for the cell
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    public PdfPTable getTable() {
        return table;
    }
}

