/*
Copyright (C) 2018 Adrian D. Finlay. All rights reserved.

Licensed under the MIT License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://opensource.org/licenses/MIT

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER INCLUDING AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
==============================================================================
**/

package src.main.java;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

//Annotation #1 - SampleAnnotation.class
@Repeatable(SampleAnnotationContainer.class)
@Retention(RetentionPolicy.RUNTIME)
//Specify the various types we are tareting (Class, Method, Field, Constructor)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
@interface SampleAnnotation { String id(); }

//Container for Repeated instances of SampleAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
@interface SampleAnnotationContainer { SampleAnnotation[] value (); }

//Annotation #2 - 
@Retention(RetentionPolicy.RUNTIME)
@interface SampleAnnotation2 { String id() default "Default ID #43216"; }

//#1 - Class Annotation
@SampleAnnotation(id = "Public Class #1834")
@SampleAnnotation(id = "Public Class #8124")

public class AnnotationReflection {

	//#2 - Constructor Annotation
	@SampleAnnotation(id = "Default Constructor #1834")
	@SampleAnnotation(id = "Default Constructor #8124")
	public AnnotationReflection () { }

	//#3 - Field Annotation
	@SampleAnnotation(id = "Field(String) #1834")
	@SampleAnnotation(id = "Field(String) #8124")
	public String cogic_friend = "Kerrie";

	//#4 - Method Annotation 
	@SampleAnnotation2
	@SampleAnnotation(id = "Main Method #1834")
	@SampleAnnotation(id = "Main Method #8124")
	public static void main(String[] args) {

		//Object to use for reflection
		AnnotationReflection an = new AnnotationReflection();
		//Container for Anotations.
		Annotation[] annos;

		//(#1) Get Annotations on AnnotationReflection Class
		annos = an.getClass().getAnnotations();
		System.out.println("\nAnnotation #1 (Class):\t");
		for (Annotation anno : annos) { System.out.println(anno + "\n"); }

		//(#2) Get Annotations on AnnotationReflection() Constructor
		try { annos = an.getClass().getConstructor().getAnnotations(); }
		catch (NoSuchMethodException nme) { System.out.println("Exception thrown: " + nme + "\n"); return; }
		System.out.println("Annotation #2 (Constructor):  ");  //The Constructor must be public.
		for (Annotation anno : annos) { System.out.println(anno + "\n"); }

		//(#3) Get Annotations on the AnnotationReflection.cogic_friend member 
		try { annos = an.getClass().getField("cogic_friend").getAnnotations(); }
		catch (NoSuchFieldException | SecurityException e) { System.out.println("Exception thrown: " + e + "\n"); return; }
		System.out.println("Annotation #3 (Field):  ");
		for (Annotation anno : annos) { System.out.println(anno + "\n"); }

		//(#4) Get Annotations on the AnnotationReflection.main() method
		try { annos = an.getClass().getMethod("main", String[].class).getAnnotations(); }
		catch (NoSuchMethodException | SecurityException e) { System.out.println("Exception thrown: " + e + "\n"); return; }
		System.out.println("Annotation #4 (Method):  ");
		for (Annotation anno : annos) { System.out.println(anno + "\n"); }

		//Using an instance of the Annotation itself
		SampleAnnotation2 sa2;
		try { sa2 = an.getClass().getMethod("main", String[].class).getAnnotation(SampleAnnotation2.class);}
		catch (NoSuchMethodException nme) { System.out.println(); return; }
		System.out.println("\nAnnotation #4 (SampleAnnotation.class):  \"" + sa2.id() + "\"\n");

	}
}