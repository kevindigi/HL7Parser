package com.digicorp.hl7parser;

public class Main {
    public static void main(String[] args) {
        //MSH|^~\&|ENGINE|013|HMS|013|20171102200005||ORU^R01|20171103000136|P|2.3
        //PID|||497974^^^ORSC03||SMITH^KALANA^N||19890426|F||B|425 ALMA BYRD LN APT A^^SPARTANBURG^SC^29301|083|(864)310-8055||E|M|B|3908060|251772775
        String[][] records = new String[][]{
                {"MSH", "^~\\&", "ENGINE", "013", "HMS", "013", "20171102200005", "ORU^R01", "20171103000136", "P", "2.3"},
                {"PID", "", "", "497974^^^ORSC03", "", "SMITH^KALANA^N", "", "19890426", "F", "", "B", "425 ALMA BYRD LN APT A^^SPARTANBURG^SC^29301", "083", "(864)310-8055", "", "E", "M", "B", "3908060", "251772775"},
                {"PV1", "", "", "EOP^^ORSC03", "", "", "", "10446^FORGIONE^LISA^M", "10446^FORGIONE^LISA^M", "", "", "", "", "", "", "", "", "10446^FORGIONE^LISA^M", "", "3908060", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
                {"ORC", "RE", "39080600000100", "39080600000100", "", "", "", "1^^^20171102200005^^S^^STAT", "", "20171102235425", "", "", "80135^KENT^JILLIAN^R", "MARY BLACK MEMORIAL HOSPITAL", "", "", "", "MARY BLACK MEMORIAL HOSPITAL"},
                {"OBR", "1", "39080600000100", "39080600000100", "IM^CT HEAD WO CONTRAST^CT^IM", "", "20171102200005", "20171102200005", "20171102200005", "", "", "", "", "", "20171102172700", "", "no referring doctor^REFDOC^EMPTY", "", "", "", "", "", "20171102200005", "", "CT", "F", "", "1^^^20171102172700^^S^^STAT", "", "", "", "^Sitting there and got real hot and dizzy, started vomiting;  c/o ha, N/V;  acute onset", "10276^NATTELL MD^DANIEL", "", "", "", "20171102235425", "", "", "", "", "", "", "", "CTHD^CT HEAD WO CONTRAST"},
                {"OBX", "1", "TX", "CTHD^CT HEAD WO CONTRAST^CT", "", "                       Mary Black Health System", "                       ", "                       ", "                       ", "                       ", "                       ", "                       F", "                       ", "                       ", "                       20171102200005", "                       ", "                       10276^NATTELL MD^DANIEL", "                       ", "                       FINAL"},
                {"OBX", "2", "TX", "CTHD^CT HEAD WO CONTRAST^CT", "", "                          1700 Skylyn Drive", "                          ", "                          ", "                          ", "                          ", "                          ", "                          F", "                          ", "                          ", "                          20171102200005", "                          ", "                          10276^NATTELL MD^DANIEL", "                          ", "                          FINAL"}
        };

        HL7RecordParser parser = new HL7RecordParser();
        for (String record[] : records) {
            if (record[0].equalsIgnoreCase("msh")) {
                parser.setSubFieldSeparators(record[1]);
            }
            parser.parseRecord(record);
        }

        parser.log();
    }
}