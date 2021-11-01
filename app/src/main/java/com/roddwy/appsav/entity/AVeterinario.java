package com.roddwy.appsav.entity;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = AVeterinario.TABLE_NAME)
public class AVeterinario {
    /** The name of the cheese table. */
    public static final String TABLE_NAME ="aveterinario";

    public static final String COLUMN_NAME = "name";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = "edad")
    private String edad;

    @ColumnInfo(name = "tipo_vacuno")
    private String tipoVacuno;

    @ColumnInfo(name = "enfermedad")
    private String enfermedad;

    @ColumnInfo(name = "alimento")
    private String alimento;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @ColumnInfo(name = "hora")
    private String hora;

    @Ignore
    public AVeterinario(){

    }

    public AVeterinario(long id, String edad, String tipoVacuno, String enfermedad, String alimento, String fecha, String hora){
        this.id = id;
        this.edad = edad;
        this.tipoVacuno = tipoVacuno;
        this.enfermedad = enfermedad;
        this.alimento = alimento;
        this.fecha = fecha;
        this.hora = hora;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTipoVacuno() {
        return tipoVacuno;
    }

    public void setTipoVacuno(String tipoVacuno) {
        this.tipoVacuno = tipoVacuno;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }
}
