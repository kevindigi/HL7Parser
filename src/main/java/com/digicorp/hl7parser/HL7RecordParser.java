/*

MSH|^~\&|ENGINE|013|HMS|013|20171102200005||ORU^R01|20171103000136|P|2.3
PID|||497974^^^ORSC03||SMITH^KALANA^N||19890426|F||B|425 ALMA BYRD LN APT A^^SPARTANBURG^SC^29301|083|(864)310-8055||E|M|B|3908060|251772775

*/


package com.digicorp.hl7parser;

import java.util.LinkedHashMap;

/**
 * @author kevin.adesara on 31/01/18.
 */
class HL7RecordParser {
    private LinkedHashMap<String, String> data = new LinkedHashMap<>();
    private String subFieldSeparators = "";

    HL7RecordParser() {
    }

    private String getSubFieldSeparators() {
        return subFieldSeparators;
    }

    /**
     * Set global sub field separators
     *
     * @param sep sub field separator like ^~\&
     */
    void setSubFieldSeparators(String sep) {
        subFieldSeparators = sep;
    }

    /**
     * Check whether sep is sub field separator or not
     *
     * @param sep separator character
     * @return true if sep is sub field separator, false otherwise
     */
    private boolean isSubFieldSeparator(String sep) {
        return subFieldSeparators.contains(sep);
    }

    void parseRecord(String[] record) {
        String key = record[0];
        for (int i = 0; i < record.length; i++) {
            String fieldKey = key + '.' + i;
            parseField(fieldKey, record[i]);
        }
    }

    private void parseField(String fieldKey, String field) {
        if (data.containsKey(fieldKey)) {
            String existingField = data.get(fieldKey);
            if (existingField != null && !existingField.trim().equalsIgnoreCase(field.trim())) {
                data.put(fieldKey, existingField + "\n" + field);
            }
        } else {
            data.put(fieldKey, field);
        }

        if (!hasSubFields(field) || field.equalsIgnoreCase(getSubFieldSeparators())) {
            return;
        }

        String[] subFields = getSubFields(field);
        if (subFields.length == 1) {
            data.put(fieldKey, field);
            return;
        }

        for (int i = 0; i < subFields.length; i++) {
            parseField(fieldKey + "." + (i + 1), subFields[i]);
        }
    }

    private boolean hasSubFields(String field) {
        for (int i = 0; i < field.length(); i++) {
            String c = Character.toString(field.charAt(i));
            if (isSubFieldSeparator(c)) {
                return true;
            }
        }
        return false;
    }

    private String getSubFieldSeparator(String field) {
        for (int i = 0; i < field.length(); i++) {
            String c = Character.toString(field.charAt(i));
            if (isSubFieldSeparator(c)) {
                return c;
            }
        }
        return null;
    }

    private String[] getSubFields(String field) {
        String subFieldSeparator = getSubFieldSeparator(field);
        if (subFieldSeparator == null) {
            return new String[]{field};
        }

        subFieldSeparator = escapeMetaCharacters(subFieldSeparator);
        return field.split(subFieldSeparator);
    }

    private String escapeMetaCharacters(String inputString) {
        final String[] metaCharacters = {"\\", "^", "$", "{", "}", "[", "]", "(", ")", ".", "*", "+", "?", "|", "<", ">", "-", "&"};
        String outputString = "";
        for (String metaCharacter : metaCharacters) {
            if (inputString.contains(metaCharacter)) {
                outputString = inputString.replace(metaCharacter, "\\" + metaCharacter);
                inputString = outputString;
            }
        }
        return outputString;
    }

    String get(String key) {
        print("Finding field for : " + key);
        if (data.containsKey(key)) {
            String field = data.get(key);
            print("Found field for : " + key + " => " + field);
            return field;
        }

        print("Not found field for : " + key);
        String[] keyParts = key.split("\\.");
        if (keyParts.length <= 2) {
            return "";
        }

        print("Trying 1 level up...");
        StringBuilder newKey = new StringBuilder(keyParts[0]);
        for (int i = 1; i < keyParts.length - 1; i++) {
            newKey.append(".").append(keyParts[i]);
        }
        return get(newKey.toString());
    }

    private void print(String info) {
        System.out.println("[" + HL7RecordParser.class.getSimpleName() + "]: " + info);
    }

    void log() {
        print(data.toString());
    }
}