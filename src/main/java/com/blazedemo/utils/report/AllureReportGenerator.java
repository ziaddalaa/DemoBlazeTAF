package com.blazedemo.utils.report;

import com.blazedemo.utils.OSUtils;
import com.blazedemo.utils.TerminalUtils;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.blazedemo.utils.dataReader.PropertyReader.getProperty;
import static com.blazedemo.utils.report.AllureConstants.HISTORY_FOLDER;
import static com.blazedemo.utils.report.AllureConstants.RESULTS_HISTORY_FOLDER;

public class AllureReportGenerator {

    //rename report file to AllureReport_timestamp.html
    public static String renameReport() {
        String newFileName = AllureConstants.REPORT_PREFIX + TimeManager.getTimeStamp() + AllureConstants.REPORT_EXTENSION; // AllureReport_20250720_211230.html
        com.blazedemo.FileUtils.renameFile(AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }

    //Generate Allure Report
    //--single-file - generate single file report
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORT_PATH;
        // allure generate -o reports --single-file --clean
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstants.RESULTS_FOLDER.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }


    //Open Allure Report
    public static void openReport(String reportFileName) {
        if (!getProperty("executionType").toLowerCase().contains("local")) return;

        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
        switch (OSUtils.getCurrentOS()) {
            case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", reportPath.toString());
            case MAC, LINUX -> TerminalUtils.executeTerminalCommand("open", reportPath.toString());
            default -> LogsManager.warn("Opening Allure Report is not supported on this OS.");
        }
    }

    //Copy history folder to the allure-results folder
    public static void copyHistory() {
        try {
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULTS_HISTORY_FOLDER.toFile());
        } catch (Exception e) {
            LogsManager.error("Error copying history files", e.getMessage());
        }
    }
}
