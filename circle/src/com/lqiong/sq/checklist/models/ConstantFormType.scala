package com.lqiong.sq.checklist.models

trait ConstantFormType extends Enumeration {
  type ConstantFormType = Value
  val checkBox = Value("CheckBox") // input
  val number = Value("Number") // input
  val textArea = Value("TextArea") // input
  val inputText = Value("InputText") // input
  val inputNumber = Value("InputNumber") // image
  val photo = Value("InputPickture") // image
}
object ConstantFormType extends ConstantFormType
