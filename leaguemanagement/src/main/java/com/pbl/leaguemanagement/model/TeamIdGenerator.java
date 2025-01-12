package com.pbl.leaguemanagement.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.time.LocalDate;

public class TeamIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Ensure the object is of type Team
        if (!(object instanceof Team)) {
            throw new IllegalArgumentException("Object is not of type Team");
        }
        Team team = (Team) object;

        // Get current year and month
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());

        // Reverse the year and month
        String reversedYear = new StringBuilder(year).reverse().toString();   // e.g., 2024 -> 4202
        String reversedMonth = new StringBuilder(month).reverse().toString(); // e.g., 09 -> 90

        // Assign team type code
        String teamTypeCode = "05"; // Code for teams

        // Generate the prefix for the current period and team type
        String prefix = reversedYear + reversedMonth + teamTypeCode; // e.g., 42029005

        // Fetch the maximum existing sequence for this prefix
        Long nextSequence = getNextSequence(session, prefix);

        // Format the sequence as a 4-digit number with leading zeros
        String sequenceStr = String.format("%04d", nextSequence);

        // Combine all parts to form the custom ID
        return prefix + sequenceStr; // e.g., 420290050001
    }

    private Long getNextSequence(SharedSessionContractImplementor session, String prefix) {
        // Query to find the maximum existing ID with the current prefix
        String hql = "SELECT MAX(t.id) FROM Team t WHERE t.id LIKE :prefixPattern";
        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter("prefixPattern", prefix + "%");
        String maxId = query.uniqueResult();

        if (maxId == null) {
            // No existing ID with this prefix, start at 1
            return 1L;
        } else {
            // Extract the sequence part and increment
            String sequencePart = maxId.substring(prefix.length());
            try {
                Long currentMax = Long.parseLong(sequencePart);
                return currentMax + 1;
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid ID format in database: " + maxId, e);
            }
        }
    }
}
