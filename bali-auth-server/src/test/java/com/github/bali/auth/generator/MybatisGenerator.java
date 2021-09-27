package com.github.bali.auth.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * Mybatis相关资源生成器
 * @author Petty
 */
public class MybatisGenerator {
    public static void main(String[] args) {
        String outputDir = "D:/Generator";
        // 全局配置
        GlobalConfig gc = new GlobalConfig.Builder()
                .author("Petty")
                .enableSwagger()
                .fileOverride()
                .outputDir(outputDir)
                .fileOverride()
                .dateType(DateType.TIME_PACK)
                .build();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig.Builder(
                "jdbc:mysql://127.0.0.1:3306/bali_user_center?characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
                "root",
                "root"
        ).build();


        TemplateConfig tc = new TemplateConfig.Builder()
                .controller("/templates/controller.java")
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .build();

        // 策略配置
        StrategyConfig strategy = new StrategyConfig.Builder()
                .addInclude("")
                .addTablePrefix("uc_")
                .entityBuilder()
                .logicDeleteColumnName("del_flag")
                .enableLombok()
                .naming(NamingStrategy.underline_to_camel)
                .superClass("com.github.bali.persistence.entity.BaseEntity")
                //.addSuperEntityColumns("id", "del_flag", "creator", "create_time", "modifier", "modify_time")
                .enableActiveRecord()
                .idType(IdType.ASSIGN_ID)
                .entityBuilder()
                .controllerBuilder()
                .enableRestStyle()
                .mapperBuilder()
                .enableBaseResultMap()
                .enableBaseColumnList()
                .build();

        // 配置包路径
        PackageConfig pc = new PackageConfig.Builder().parent("com.github.bali.auth").build();

        AutoGenerator mpg = new AutoGenerator(dsc).global(gc).strategy(strategy).packageInfo(pc).template(tc);
        mpg.execute(new FreemarkerTemplateEngine());
    }
}

