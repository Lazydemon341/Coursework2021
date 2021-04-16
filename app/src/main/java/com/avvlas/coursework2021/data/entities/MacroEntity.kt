package com.avvlas.coursework2021.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.domain.model.options.actions.Action
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger

@Entity(tableName = "macros")
@TypeConverters(OptionsConverter::class)
class MacroEntity(
    var name: String,
    var triggers: ArrayList<Trigger>,
    var actions: ArrayList<Action>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun MacroEntity.toMacro(): Macro =
    Macro(
        name = this.name,
        triggers = this.triggers,
        actions = this.actions
    )

fun Macro.toMacroEntity(): MacroEntity =
    MacroEntity(
        name = this.name,
        triggers = this.triggers,
        actions = this.actions
    )