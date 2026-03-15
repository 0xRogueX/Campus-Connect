package com.campusconnect.resultservice.service.impl;

import com.campusconnect.resultservice.entity.Result;
import com.campusconnect.resultservice.entity.Subject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
public class ResultPdfService {

    // Font definitions
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.DARK_GRAY);

    // Colors
    private static final BaseColor HEADER_COLOR = new BaseColor(0, 51, 102); // Dark blue
    private static final BaseColor LIGHT_BLUE = new BaseColor(232, 242, 254);
    private static final BaseColor BORDER_COLOR = new BaseColor(210, 210, 210);

    public ByteArrayInputStream generateResultPdf(Result result) {
        Document document = new Document(PageSize.A4, 36, 36, 54, 36); // Margins: left, right, top, bottom
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);

            // Add header and footer
            writer.setPageEvent(new HeaderFooterPageEvent(result));

            document.open();
            addMetaData(document);

            // Add watermark
            PdfContentByte watermark = writer.getDirectContentUnder();
            addWatermark(watermark, document);

            addUniversityHeader(document, result);
            addTitlePage(document, result);
            addStudentDetails(document, result);
            addSubjectsTable(document, result);
            addResultSummary(document, result);
            addSignatureSection(document);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addMetaData(Document document) {
        document.addTitle("Student Result Card");
        document.addAuthor("Campus Connect");
        document.addCreator("Campus Connect Result Service");
        document.addSubject("Examination Result");
        document.addKeywords("result, examination, academic");
    }

    private void addWatermark(PdfContentByte watermark, Document document) {
        try {
            Font watermarkFont = new Font(Font.FontFamily.HELVETICA, 60, Font.BOLD, new BaseColor(230, 230, 230));
            Phrase watermarkText = new Phrase("OFFICIAL RECORD", watermarkFont);

            // Rotate and position the watermark
            watermark.saveState();
            watermark.setGState(new PdfGState() {{ setFillOpacity(0.1f); }});

            ColumnText.showTextAligned(watermark, Element.ALIGN_CENTER, watermarkText,
                    document.getPageSize().getWidth() / 2,
                    document.getPageSize().getHeight() / 2, 45);

            watermark.restoreState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addUniversityHeader(Document document, Result result) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1, 3, 1});

        // Logo placeholder (Left)
        PdfPCell logoCell = new PdfPCell();
        try {
            // Try to load university logo
            // Replace with actual logo loading or use a placeholder
            Image logo = Image.getInstance(new byte[0]); // Replace with actual logo bytes
            logo.scaleToFit(60, 60);
            logoCell = new PdfPCell(logo);
        } catch (Exception e) {
            // Use a placeholder text instead
            Paragraph logoPh = new Paragraph("LOGO", BOLD_FONT);
            logoCell = new PdfPCell(logoPh);
        }
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        logoCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(logoCell);

        // University name (Center)
        PdfPCell universityCell = new PdfPCell();
        Paragraph universityName = new Paragraph("CAMPUS CONNECT UNIVERSITY", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, HEADER_COLOR));
        universityName.setAlignment(Element.ALIGN_CENTER);
        Paragraph universityAddress = new Paragraph("University Address, City, Country - PIN", SMALL_FONT);
        universityAddress.setAlignment(Element.ALIGN_CENTER);
        universityCell.addElement(universityName);
        universityCell.addElement(universityAddress);
        universityCell.setBorder(Rectangle.NO_BORDER);
        universityCell.setPaddingBottom(10);
        universityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        universityCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTable.addCell(universityCell);

        // Academic year (Right)
        PdfPCell academicYearCell = new PdfPCell();
        Paragraph academicYear = new Paragraph("Academic Year\n2024-2025", BOLD_FONT);
        academicYear.setAlignment(Element.ALIGN_CENTER);
        academicYearCell.addElement(academicYear);
        academicYearCell.setBorder(Rectangle.NO_BORDER);
        academicYearCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        academicYearCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTable.addCell(academicYearCell);

        document.add(headerTable);

        // Add a separator line
        LineSeparator lineSeparator = new LineSeparator(1, 100, HEADER_COLOR, Element.ALIGN_CENTER, -5);
        document.add(new Chunk(lineSeparator));
        document.add(Chunk.NEWLINE);
    }

    private void addTitlePage(Document document, Result result) throws DocumentException {
        Paragraph title = new Paragraph("STATEMENT OF GRADES", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);

        Paragraph examInfo = new Paragraph(result.getExam() + " - " + result.getSemester(), SUBTITLE_FONT);
        examInfo.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(examInfo);
        document.add(Chunk.NEWLINE);
    }

    private void addStudentDetails(Document document, Result result) throws DocumentException {
        // Student details in a table with border
        PdfPTable mainTable = new PdfPTable(1);
        mainTable.setWidthPercentage(100);

        // Create a cell for the entire student details section
        PdfPCell mainCell = new PdfPCell();
        mainCell.setBorderColor(BORDER_COLOR);
        mainCell.setPadding(10);

        // Inner table for student details with 2 columns
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);

        // Add student details
        addStudentDetail(detailsTable, "Enrollment Number:", String.valueOf(result.getEnrollmentNo()));
        addStudentDetail(detailsTable, "Student ID:", result.getStudentId());
        addStudentDetail(detailsTable, "Branch:", result.getBranch());
        addStudentDetail(detailsTable, "Semester:", result.getSemester());

        String declaredDate = "";
        if (result.getDeclaredOn() != null) {
            declaredDate = result.getDeclaredOn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        addStudentDetail(detailsTable, "Result Declared On:", declaredDate);

        String status = "PENDING";
        if (result.getIsPass() != null) {
            status = result.getIsPass() ? "PASS" : "FAIL";
        }

        // Status with highlighted background
        PdfPCell statusLabelCell = new PdfPCell(new Phrase("Status:", BOLD_FONT));
        statusLabelCell.setBorder(Rectangle.ALIGN_RIGHT);
        statusLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        detailsTable.addCell(statusLabelCell);

        PdfPCell statusValueCell = new PdfPCell();
        Paragraph statusPara = new Paragraph(status, BOLD_FONT);
        statusPara.setAlignment(Element.ALIGN_LEFT);
        statusValueCell.setBorder(Rectangle.ALIGN_RIGHT);

        if ("PASS".equals(status)) {
            statusValueCell.setBackgroundColor(new BaseColor(220, 255, 220)); // Light green for pass
        } else if ("FAIL".equals(status)) {
            statusValueCell.setBackgroundColor(new BaseColor(255, 220, 220)); // Light red for fail
        }

        statusValueCell.addElement(statusPara);
        statusValueCell.setBorder(Rectangle.ALIGN_RIGHT);
        statusValueCell.setPadding(5);
        detailsTable.addCell(statusValueCell);

        // Add the inner table to the main cell
        mainCell.addElement(detailsTable);
        mainTable.addCell(mainCell);

        document.add(mainTable);
        document.add(Chunk.NEWLINE);
    }

    private void addStudentDetail(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, BOLD_FONT));
        labelCell.setBorder(Rectangle.ALIGN_RIGHT);
        labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        labelCell.setPadding(5);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, NORMAL_FONT));
        valueCell.setBorder(Rectangle.ALIGN_RIGHT);
        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        valueCell.setPadding(5);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addSubjectsTable(Document document, Result result) throws DocumentException {
        Paragraph subjectsHeader = new Paragraph("COURSE GRADES", HEADER_FONT);
        subjectsHeader.setAlignment(Element.ALIGN_LEFT);
        document.add(subjectsHeader);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(10); // Added grade column
        table.setWidthPercentage(100);

        try {
            // Set column widths
            float[] columnWidths = {0.5f, 2.5f, 0.5f, 0.7f, 0.7f, 0.7f, 0.7f, 0.7f, 0.7f, 0.5f};
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Add table headers
        String[] headers = {"Code", "Subject Name", "Credits", "Theory SE", "Theory PA",
                "Theory Total", "Practical SE", "Practical PA", "Practical Total", "Grade"};

        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(HEADER_COLOR);
            headerCell.setBorderWidth(1);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);
            headerCell.setPhrase(new Phrase(header, headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }

        // Add data rows
        boolean alternate = false;
        for (Subject subject : result.getSubjects()) {
            BaseColor rowColor = alternate ? LIGHT_BLUE : BaseColor.WHITE;
            alternate = !alternate;

            if (subject.getIsAbsent() != null && subject.getIsAbsent()) {
                addColoredSubjectRow(table, subject, "ABSENT", rowColor);
            } else {
                addColoredSubjectRow(table, subject, subject.getSubjectGrade(), rowColor);
            }
        }

        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addColoredSubjectRow(PdfPTable table, Subject subject, String grade, BaseColor color) {
        // Add each cell with the specified background color
        addColoredCell(table, String.valueOf(subject.getSubjectCode()), color);
        addColoredCell(table, subject.getSubjectName(), color);
        addColoredCell(table, String.valueOf(subject.getCredit()), color);
        addColoredCell(table, subject.getTese(), color);
        addColoredCell(table, subject.getTpaca(), color);
        addColoredCell(table, subject.getTtotal(), color);
        addColoredCell(table, subject.getPesa(), color);
        addColoredCell(table, subject.getPpaca(), color);
        addColoredCell(table, subject.getPtotal(), color);

        // Add grade with special formatting
        PdfPCell gradeCell = new PdfPCell(new Phrase(grade, BOLD_FONT));
        gradeCell.setBackgroundColor(color);
        gradeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        gradeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        gradeCell.setPadding(5);
        table.addCell(gradeCell);
    }

    private void addColoredCell(PdfPTable table, String text, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(text, NORMAL_FONT));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addResultSummary(Document document, Result result) throws DocumentException {
        // Create a bordered box for result summary
        PdfPTable summaryContainer = new PdfPTable(1);
        summaryContainer.setWidthPercentage(40);
        summaryContainer.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell summaryCell = new PdfPCell();
        summaryCell.setBorderColor(BORDER_COLOR);
        summaryCell.setPadding(10);

        // Summary Table inside the box
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Add summary headers
        PdfPCell headerCell = new PdfPCell(new Phrase("RESULT SUMMARY", HEADER_FONT));
        headerCell.setColspan(2);
        headerCell.setBackgroundColor(HEADER_COLOR);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setPadding(5);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        headerCell.setPhrase(new Phrase("RESULT SUMMARY", headerFont));
        table.addCell(headerCell);

        // Add SPI
        Font whiteFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        PdfPCell spiLabelCell = new PdfPCell(new Phrase("SPI:", whiteFont));
        spiLabelCell.setBackgroundColor(HEADER_COLOR);
        spiLabelCell.setPadding(5);
        table.addCell(spiLabelCell);

        String spiValue = "N/A";
        if (result.getSpi() != null) {
            spiValue = String.format("%.2f", result.getSpi());
        }
        table.addCell(createSummaryValueCell(spiValue));

        // Add CPI if available
        if (result.getCpi() != null) {
            PdfPCell cpiLabelCell = new PdfPCell(new Phrase("CPI:", whiteFont));
            cpiLabelCell.setBackgroundColor(HEADER_COLOR);
            cpiLabelCell.setPadding(5);
            table.addCell(cpiLabelCell);
            table.addCell(createSummaryValueCell(String.format("%.2f", result.getCpi())));
        }

        // Add CGPA if available
        if (result.getCgpa() != null) {
            PdfPCell cgpaLabelCell = new PdfPCell(new Phrase("CGPA:", whiteFont));
            cgpaLabelCell.setBackgroundColor(HEADER_COLOR);
            cgpaLabelCell.setPadding(5);
            table.addCell(cgpaLabelCell);
            table.addCell(createSummaryValueCell(String.format("%.2f", result.getCgpa())));
        }

        summaryCell.addElement(table);
        summaryContainer.addCell(summaryCell);

        document.add(summaryContainer);
        document.add(Chunk.NEWLINE);
    }

    private PdfPCell createSummaryValueCell(String value) {
        PdfPCell cell = new PdfPCell(new Phrase(value, BOLD_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }


    private void addGradeRow(PdfPTable table, String grade, String description, String points, String range) {
        boolean isFailGrade = "FF".equals(grade);
        BaseColor rowColor = isFailGrade ? new BaseColor(255, 220, 220) : BaseColor.WHITE;
        Font font = isFailGrade ? BOLD_FONT : NORMAL_FONT;

        PdfPCell gradeCell = new PdfPCell(new Phrase(grade, font));
        gradeCell.setBackgroundColor(rowColor);
        gradeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(gradeCell);

        PdfPCell descCell = new PdfPCell(new Phrase(description, font));
        descCell.setBackgroundColor(rowColor);
        table.addCell(descCell);

        PdfPCell pointsCell = new PdfPCell(new Phrase(points, font));
        pointsCell.setBackgroundColor(rowColor);
        pointsCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(pointsCell);

        PdfPCell rangeCell = new PdfPCell(new Phrase(range, font));
        rangeCell.setBackgroundColor(rowColor);
        rangeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(rangeCell);
    }

    private void addSignatureSection(Document document) throws DocumentException {
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        PdfPTable signatureTable = new PdfPTable(3);
        signatureTable.setWidthPercentage(100);

        // Add three signature slots
        addSignatureSlot(signatureTable, "Student");
        addSignatureSlot(signatureTable, "Examination Controller");
        addSignatureSlot(signatureTable, "Principal");

        document.add(signatureTable);

        // Add disclaimer
        document.add(Chunk.NEWLINE);
        Paragraph disclaimer = new Paragraph("This is a computer-generated document. No signature is required.", SMALL_FONT);
        disclaimer.setAlignment(Element.ALIGN_CENTER);
        document.add(disclaimer);
    }

    private void addSignatureSlot(PdfPTable table, String title) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        // Add space for signature
        Paragraph space = new Paragraph(" ");
        space.setSpacingAfter(30);
        cell.addElement(space);

        // Add line
        PdfPTable lineTable = new PdfPTable(1);
        lineTable.setWidthPercentage(80);
        PdfPCell lineCell = new PdfPCell();
        lineCell.setBorderWidthBottom(1);
        lineCell.setBorderColorBottom(BaseColor.BLACK);
        lineCell.setBorderWidthTop(0);
        lineCell.setBorderWidthLeft(0);
        lineCell.setBorderWidthRight(0);
        lineCell.setFixedHeight(1);
        lineTable.addCell(lineCell);
        cell.addElement(lineTable);

        // Add title
        Paragraph titlePara = new Paragraph(title, NORMAL_FONT);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(titlePara);

        table.addCell(cell);
    }

    // Class for page header and footer
    class HeaderFooterPageEvent extends PdfPageEventHelper {
        private Result result;

        public HeaderFooterPageEvent(Result result) {
            this.result = result;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();

            // Add footer
            Phrase footer = new Phrase("Page " + writer.getPageNumber() + " | Generated on: " +
                    java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), SMALL_FONT);

            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);

            // Add enrollment number in the lower right corner
            Phrase enrollment = new Phrase("Enrollment: " + result.getEnrollmentNo(), SMALL_FONT);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, enrollment,
                    document.right(), document.bottom() - 10, 0);
        }
    }
}