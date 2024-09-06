package com.hiddenartist.backend.global.db;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.MySQLDialect;

public class CustomMySQLDialect extends MySQLDialect {

  private static final String FUNCTION_NAME = "match";
  private static final String FUNCTION_PATTERN = "match (?1) against (?2 in boolean mode)";

  @Override
  public void contributeFunctions(FunctionContributions functionContributions) {
    super.contributeFunctions(functionContributions);
    functionContributions.getFunctionRegistry().registerPattern(
        FUNCTION_NAME,
        FUNCTION_PATTERN
    );
  }
}