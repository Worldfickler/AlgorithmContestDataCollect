package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Enum.UnRecommendationReason;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.*;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CodeforcesContestTotalQuestionResultHandler extends AbstractHandler {
    Logger logger = LoggerFactory.getLogger(CodeforcesContestTotalQuestionResultHandler.class);
    @Autowired
    CodeforcesProblemRepository codeforcesProblemRepository;
    @Autowired
    ProblemTagRepository problemTagRepository;

    @Autowired
    CodeforcesProblemsTagMapRepository codeforcesProblemsTagMapRepository;
    @Autowired
    NoRecommendContestRepository noRecommendContestRepository;
    @Autowired
    CfProblemNoTagRepository cfProblemNoTagRepository;
    @Autowired
    CfContestRepository cfContestRepository;
    @Value("${ChromeDriver}")
    String ChromeDriver;
    @Value("${CodeforcesUserName}")
    String CodeforcesUserName;
    @Value("${CodeforcesPassword}")
    String CodeforcesPassword;
    @Value("${Chrome}")
    String Chrome;
    @Override
    public void handle(String result) {
        JSONArray res = (JSONArray) JSONArray.parse(result);
        var problems = codeforcesProblemRepository.findAll();
        var tags = problemTagRepository.findAll();
        Map<String, Integer> NoTagHashMap = cfProblemNoTagRepository.findAll().stream().collect(Collectors.toMap(CfProblemNoTag::getCidQindex, CfProblemNoTag::getId));
        HashSet<NoRecommendContest> fresh = new HashSet<>();
        Set<String> problemSet = new HashSet<>();
        Map<String, Integer> tagMp = new HashMap<>();
        for (var problem : problems) {
            String metaInfo = "" + problem.getCid() + "-" + problem.getQindex() + "-" + problem.getDifficulty();
            problemSet.add(metaInfo);
        }
        for (var tag : tags) {
            tagMp.put(tag.getTagName(), tag.getId());
        }
        for (int i = 0; i < res.size(); i++) {
            JSONObject problem = res.getJSONObject(i);
            if (!problem.getString("type").equals("PROGRAMMING")) {
                continue;
            }
            Integer difficulty = problem.getInteger("rating");
            if (difficulty == null) {
                difficulty = 0;
                fresh.add(new NoRecommendContest(problem.getLong("contestId"),UnRecommendationReason.DIFFICULTY_IS_ZERO.getValue()));
            }
            String metaInfo = "" + problem.getLong("contestId") + "-" + problem.getString("index") + "-" + difficulty;
            if (!problemSet.contains(metaInfo)) {
                //题目是否更新分数
                if (codeforcesProblemRepository.existsByCidAndQindex(problem.getLong("contestId"), problem.getString("index"))) {
                    logger.info("更新CodeforcesProblem{}", metaInfo);
                    var row = codeforcesProblemRepository.getByCidAndQindex(problem.getLong("contestId"), problem.getString("index"));
                    row.setDifficulty(difficulty);
                    codeforcesProblemRepository.save(row);
                } else {
                    //第一次添加这个题目
                    logger.info("添加CodeforcesProblem{}", metaInfo);
                    CodeforcesProblems entity = new CodeforcesProblems(problem.getLong("contestId"), problem.getString("index"), difficulty, problem.getString("name"));
                    var codeforcesProblem = codeforcesProblemRepository.saveAndFlush(entity);
                    var problem_tags = problem.getJSONArray("tags");
                    for (int j = 0; j < problem_tags.size(); j++) {
                        String tag = problem_tags.getString(j);
                        if (!tagMp.containsKey(tag)) {
                            logger.info("添加CodeforcesProblemTag{}", tag);
                            var e = problemTagRepository.saveAndFlush(new ProblemTag(tag));
                            tagMp.put(tag, e.getId());
                        }
                        var newPK = new CodeforcesProblemsTagMap(entity.getId(), tagMp.get(tag));
                        codeforcesProblemsTagMapRepository.saveAndFlush(newPK);
                    }
                }
            } else if (problemSet.contains(metaInfo) && NoTagHashMap.containsKey(problem.getLong("contestId") + problem.getString("index")) && problem.getJSONArray("tags").size() > 0) {
                //补标签
                JSONArray problem_tags = problem.getJSONArray("tags");
                Integer problemId = NoTagHashMap.get(problem.getLong("contestId") + problem.getString("index"));
                for (int j = 0; j < problem_tags.size(); j++) {
                    String tag = problem_tags.getString(j);
                    if (!tagMp.containsKey(tag)) {
                        logger.info("添加CodeforcesProblemTag{}", tag);
                        var e = problemTagRepository.saveAndFlush(new ProblemTag(tag));
                        tagMp.put(tag, e.getId());
                    }
                    var newPK = new CodeforcesProblemsTagMap(problemId, tagMp.get(tag));
                    codeforcesProblemsTagMapRepository.saveAndFlush(newPK);
                }
            }
        }
        updateAllInvalidContest(new ArrayList<>(fresh));
        codeforcesProblemRepository.flush();
    }
    /**
     * description:每次更新题目，将模拟点击最新的50道题，无法访问的比赛题目不推荐，专门比赛题目不推荐，传过来的是difficult等于0的，原来数据库中difficult等于0，是题目未更新，等题目更新后也许它不是0了。
     * author:yjz
     * @param difficultIsZeros
     * @return void
     */
    public void updateAllInvalidContest(List<NoRecommendContest> difficultIsZeros) {
        List<Long> contests = cfContestRepository.findByOrderByCidDesc(PageRequest.of(0, 20)).stream().map(a -> a.getCid()).collect(Collectors.toList());
        List<Long> byReason = noRecommendContestRepository.findByReason(UnRecommendationReason.FORBIDDEN.getValue()).stream().map(a->a.getCid()).collect(Collectors.toList());
//        List<Long> contests = cfContestRepository.findAll().stream().map(a -> a.getCid()).collect(Collectors.toList());
        ArrayList<NoRecommendContest> res = new ArrayList<>();
        WebDriver driver=null;
        try {
            System.setProperty("webdriver.chrome.driver", ChromeDriver);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(true); // 设置无头模式
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.setBinary(Chrome);
            // 初始化WebDriver
            driver = new ChromeDriver(chromeOptions);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 设置等待时间为10秒
            String loginUrl = "https://codeforces.com/enter";
            driver.get(loginUrl);
            WebElement handleOrEmail = driver.findElement(new By.ById("handleOrEmail"));
            handleOrEmail.sendKeys(CodeforcesUserName);
            WebElement password = driver.findElement(new By.ById("password"));
            password.sendKeys(CodeforcesPassword);
            WebElement submit = driver.findElement(new By.ByClassName("submit"));
            submit.click();
            wait.until(ExpectedConditions.titleIs("Codeforces"));
            for (int i = 0; i < contests.size(); i++) {
                String originalUrl = "https://codeforces.com/contest/" + contests.get(i) + "/submit";
                driver.get(originalUrl);
                String currentUrl = driver.getCurrentUrl();
                // 如果URL被重定向
                if (!currentUrl.equals(originalUrl)) {
                    res.add(new NoRecommendContest(contests.get(i), UnRecommendationReason.FORBIDDEN.getValue()));
                    continue;
                } else {
                    WebElement programTypeId = driver.findElement(new By.ByName("programTypeId"));
                    Select select = new Select(programTypeId);
                    List<WebElement> options = select.getOptions();
                    int optionCount = options.size();
                    if (optionCount < 5) {
                        //特定语言比赛
                        res.add(new NoRecommendContest(contests.get(i), UnRecommendationReason.SPECIAL_LANGUAGE_COMPETITION.getValue()));
                        continue;
                    }
                }
                //走到这里代表这个比赛是合理的。
                if (byReason.contains(contests.get(i))) {
                    noRecommendContestRepository.deleteByCid(contests.get(i));
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            // 关闭WebDriver
            driver.quit();
        }
        res.addAll(difficultIsZeros);
        noRecommendContestRepository.deleteByReason(UnRecommendationReason.DIFFICULTY_IS_ZERO.getValue());
        noRecommendContestRepository.saveAllAndFlush(res);

    }
}
