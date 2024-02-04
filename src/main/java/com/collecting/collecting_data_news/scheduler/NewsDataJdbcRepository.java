package com.collecting.collecting_data_news.scheduler;

import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.collecting.collecting_data_news.common.function.UtilityFunction.format_yyyy_MM_dd_HH_mm_ss;
import static java.time.LocalDateTime.*;


@Repository
@RequiredArgsConstructor
public class NewsDataJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate template;

    /**
     * JPA bulk insert 한계로 인해
     * JDBC 구현
     */
    public void batchInsertNewsData(List<DataCollectedDto> dataCollectedDtoList) {
        String sql = """
                            INSERT INTO `news` ( `keyword_idx`, `href`, `newspaper`, `title`,`search_newspaper`,`created_date`,`modified_date`)
                            VALUES
                                (?, ?, ?, ?, ?, ?, ?)
                            ON DUPLICATE KEY UPDATE idx = idx
                            ;
                """;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DataCollectedDto dataCollectedDto = dataCollectedDtoList.get(i);
                ps.setLong(1, dataCollectedDto.getKeywordIdx());
                ps.setString(2, dataCollectedDto.getHref());
                ps.setString(3, dataCollectedDto.getNewspaper());
                ps.setString(4, dataCollectedDto.getTitle());
                ps.setObject(5, dataCollectedDto.getSearchNewsPaper().label());
                ps.setObject(6, now());
                ps.setObject(7, now());
            }

            @Override
            public int getBatchSize() {
                return dataCollectedDtoList.size();
            }
        });
    }
}




