package com.example.a7minuteworkout

class ExerciseModel(
    private var id: Int,
    private var name : String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean)
{
    fun setId(id: Int){
        this.id = id
    }

    fun getId():Int{
        return id
    }

    fun setName(name : String){
        this.name = name
    }

    fun getName():String{
        return name
    }

    fun setImage(image: Int){
        this.image = image
    }

    fun getImage():Int{
        return image
    }

    fun setIsCompleted(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }

    fun getIsCompleted():Boolean{
        return isCompleted
    }

    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }

    fun getIsSelected() : Boolean{
        return isSelected
    }

}