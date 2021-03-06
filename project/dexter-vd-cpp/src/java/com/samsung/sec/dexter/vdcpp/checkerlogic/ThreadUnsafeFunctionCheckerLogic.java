/**
 *  @file   ThreadUnsafeFunction.java
 *  @brief  ThreadUnsafeFunction class source file
 *  @author adarsh.t
 *
 * Copyright 2015 by Samsung Electronics, Inc.
 * All rights reserved.
 * 
 * Project Description :
 * This software is the confidential and proprietary information
 * of Samsung Electronics, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Samsung Electronics.
 */

package com.samsung.sec.dexter.vdcpp.checkerlogic;

import java.util.Map;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import com.samsung.sec.dexter.core.analyzer.AnalysisConfig;
import com.samsung.sec.dexter.core.analyzer.AnalysisResult;
import com.samsung.sec.dexter.core.checker.IChecker;
import com.samsung.sec.dexter.core.defect.PreOccurence;
import com.samsung.sec.dexter.vdcpp.plugin.DexterVdCppPlugin;
import com.samsung.sec.dexter.vdcpp.util.CppUtil;


public class ThreadUnsafeFunctionCheckerLogic implements ICheckerLogic{

	private String[] lstMethods=null;
	private IASTTranslationUnit translationUnit;		


	@Override
	public void analyze(final AnalysisConfig config, final AnalysisResult result, 
			final IChecker checker, IASTTranslationUnit unit) {
		translationUnit =unit;
		lstMethods= checker.getProperty("method-list").split(",");
		ASTVisitor visitor = createVisitor(config, result, checker);
		visitor.shouldVisitDeclarations = true;
		unit.accept(visitor);
	}

	private ASTVisitor createVisitor(final AnalysisConfig config,
			final AnalysisResult result, final IChecker checker) {
		ASTVisitor visitor = new ASTVisitor() {
			@Override
			public int visit(IASTDeclaration ast ) {
				
				if(ast instanceof IASTFunctionDefinition)
				{									
					visitFunction(config, result, checker, ast);
				}		
				else if(ast instanceof IASTSimpleDeclaration)
				{												
					//visitOtherCompoundDeclaration(config, result, checker, ast);				
				}


				return super.visit(ast);
			}

			/*
			private void visitOtherCompoundDeclaration(
					final AnalysisConfig config, final AnalysisResult result,
					final Checker checker, IASTDeclaration ast) {
				String functionName ="";							
				IASTDeclarator[]   exp = ((IASTSimpleDeclaration) ast).getDeclarators();		
				for (IASTDeclarator iastDeclarator : exp)
				{								
					functionName =iastDeclarator.getName().toString();
					for (String  methodName : lstMethods)
					{					
						if(functionName.equals(methodName))
						{				

							String msg = methodName+": dbus-glib is thread unsafe function. so VD recommends NOT to use dbus-glib in the multi-thread environment.";
							fillDefectData( config, result,checker,iastDeclarator.getFileLocation(),msg,functionName);

						}
					}


				}


			}
			 */
			
			
			private void visitFunction(final AnalysisConfig config,
					final AnalysisResult result, final IChecker checker,
					IASTDeclaration ast) {
				ASTVisitor visitor = new ASTVisitor() {
					public int visit(IASTExpression astExpression ) {							

						if(astExpression instanceof IASTFunctionCallExpression)
						{				
							visitFunctionCallExpressions(config, result,
									checker, astExpression);
						}

						return ASTVisitor.PROCESS_CONTINUE;

					}

					private void visitFunctionCallExpressions(
							final AnalysisConfig config,
							final AnalysisResult result, final IChecker checker,
							IASTExpression astExpression) {
						IASTExpression functionCallExpression =   ((IASTFunctionCallExpression) astExpression).getFunctionNameExpression();		

						String functionName =functionCallExpression.getRawSignature();
						if(functionCallExpression instanceof IASTIdExpression)
						{
							functionName =((IASTIdExpression) functionCallExpression).getName().toString();
						}
						
						for (String  methodName : lstMethods)
						{					
							if(functionName.equals(methodName))
							{													
								String msg =methodName+": dbus-glib is thread unsafe function. so VD recommends NOT to use dbus-glib in the multi-thread environment.";
								fillDefectData( config, result,checker,astExpression.getFileLocation(),msg,methodName);
							}

						}
					}						

				};
				visitor.shouldVisitExpressions = true; 					
				ast.accept(visitor);
			}

			private void fillDefectData(AnalysisConfig config,
					AnalysisResult result, IChecker checker,
					IASTFileLocation fileLocation, String message, String declaratorName) {
				
				PreOccurence preOcc = createPreOccurence(config, checker, fileLocation, message,declaratorName);
				result.addDefectWithPreOccurence(preOcc);

			}

			private PreOccurence createPreOccurence(AnalysisConfig config,
					IChecker checker, IASTFileLocation fileLocation, String msg,String declaratorName) {
				final int startLine = fileLocation.getStartingLineNumber();
				final int endLine = fileLocation.getEndingLineNumber();
				final int startOffset = fileLocation.getNodeOffset();
				final int endOffset = startOffset + fileLocation.getNodeLength();

				Map<String,String> tempmap =CppUtil.extractModuleName(translationUnit, startLine);
				String className =tempmap.get("className");
				String methodName =tempmap.get("methodName");

				PreOccurence preOcc = new PreOccurence();
				preOcc.setCheckerCode(checker.getCode());
				preOcc.setFileName(config.getFileName());
				preOcc.setModulePath(config.getModulePath());
				preOcc.setClassName(className);
				preOcc.setMethodName(methodName);
				preOcc.setLanguage(config.getLanguageEnum().toString());
				preOcc.setSeverityCode(checker.getSeverityCode());
				preOcc.setMessage(checker.getDescription());
				preOcc.setToolName(DexterVdCppPlugin.PLUGIN_NAME);

				preOcc.setStartLine(startLine);
				preOcc.setEndLine(endLine);
				preOcc.setCharStart(startOffset);
				preOcc.setCharEnd(endOffset);
				preOcc.setVariableName(declaratorName);
				preOcc.setStringValue(msg);
				preOcc.setMessage(msg);

				return preOcc;

			}

		};

		return visitor;
	}


}
