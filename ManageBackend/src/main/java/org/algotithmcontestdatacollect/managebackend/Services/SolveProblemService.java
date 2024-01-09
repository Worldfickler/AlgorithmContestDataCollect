package org.algotithmcontestdatacollect.managebackend.Services;

import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CodeforcesProblems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SolveProblemService {
    @Autowired
    EntityManagerFactory entityManagerFactory;
    String sql = """
            SELECT
            	uid,
            	GROUP_CONCAT( CONCAT( concat_table.concat_value ) SEPARATOR ',' ) AS solve_problems
            FROM
            	(
            	SELECT
            		uid,
            		CONCAT( cid, q_index ) AS concat_value
            	FROM
            		( SELECT DISTINCT uid, cid, q_index FROM cf_submit_with_userinfo WHERE
            		`status` = "OK" and
            		`submit_time` >= :startTime and
            		`submit_time` <= :endTime and
            		uid in :uids
            		) AS distinct_table
            	) AS concat_table
            GROUP BY
            	uid
            """;
    public Map<Long,String> getCodeforcesSolveProblemCountByUserIdsAndStartTimeAndEndTime(List<Long> uids,Long startTime,Long endTime) {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createNativeQuery(sql);
        query.setParameter("startTime",startTime);
        query.setParameter("endTime",endTime);
        query.setParameter("uids",uids);
        var result = query.getResultList();
        Map<Long,String> ret = new HashMap<>();
        for(var item : result){
            var row = (Object[])item;
            ret.put(((BigInteger)row[0]).longValue(),(row[1]).toString());
        }
        entityManager.close();
        return ret;
    }

    public List<CodeforcesProblems> findProblems(HashSet<String> problems) {
        Pattern r = Pattern.compile("(\\d*)([A-Z]+[1-9]?)");
        var entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CodeforcesProblems> query = cb.createQuery(CodeforcesProblems.class);
        Root<CodeforcesProblems> root = query.from(CodeforcesProblems.class);
        List<Predicate> predicates = new ArrayList<>();
        for (String temp : problems) {
            Matcher m = r.matcher(temp);
            m.find();
            var contestId = Long.parseLong(m.group(1));
            var index = m.group(2);
            Predicate predicate = cb.and(
                    cb.equal(root.get("cid"), contestId),
                    cb.equal(root.get("qindex"), index)
            );
            predicates.add(predicate);
        }
        Predicate finalPredicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        query.where(finalPredicate);
        List<CodeforcesProblems> resultList = entityManager.createQuery(query).getResultList();
        entityManager.close();
        return resultList;
    }


}
