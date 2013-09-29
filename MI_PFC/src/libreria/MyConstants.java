package libreria;

public class MyConstants {

	private MyConstants() {
        throw new AssertionError();
    }
	
	public interface ConstantType {}
	
	public enum IntConstant implements ConstantType {
        MAX_LENGTH_TEXT_FIELD(45), 
        MAX_LENGTH_TEXT_PANE(349),
        MAX_LENGHT_TEXT_ERROR_MSG(200);
        // other int constants come here

        private int value;
        private IntConstant(int value) {
            this.value = value;
        }
        public int value() {
            return value;
        }
    }
	
	public enum StringConstant implements ConstantType {
		INICIO("Inicio"),
        ALTA("Alta"),
        BAJA("Baja"),
        VER_MODIFICAR("Ver/Modificar"),
        MENSAJE_ACIERTO_FILAS("La(s) fila(s) seleccionada(s) ha(n) sido eliminadas correctamente."),
        MENSAJE_BAJA_ERROR_FILAS("La(s) fila(s) seleccionada(s) no ha(n) sido eliminadas correctamente."),
        FABRICANTE_ID("Fabricante id = "),
        MODELO_ID("Modelo id = "),
        SERIE_ID("Serie id = "),
        ESTRUCTURA_FRAME_ID("Estructura Frame id = "),
        MANUAL_ID("Manual id = "),
        SISTEMA_ID("Sistema id = "),
        ELEMENTOS_ID("Elementos Sistema id = "),
        ESTRUCTURA_ATRIBUTOS_ID("Estructura Atributo id = "),
        FABRICANTE("El Fabricante"),
        MODELO("El Modelo"),
        SERIE("La Serie"),
        ESTRUCTURA_FRAME("La Estructura de Frame"),
        MANUAL("El Manual"),
        SISTEMA("El Sistema Específico"),
        ELEMENTOS("El Elemento Especifico"),
        //ESTRUCTURA_ATRIBUTOS("La Estructura de Atributo"),
        MENSAJE_BAJA("¿Desea borrar las filas seleccionadas?"),
        MENSAJE_CIERRE_APLICACION("¿Desea cerrar la aplicación?"),
        TITULO_VENTANA_CONFIRMACION("Mensaje de Confirmación"),
        MENSAJE_ELIMINAR_BBDD("¿Desea borrar la estructura de bbdd seleccionada?"),
        MENSAJE_UNIQUE_BAJA_ELEMENTO_ESPECIFICO("El elemento escogido está siendo utilizado. Inténtelo de nuevo más tarde."),
        MENSAJE_UNIQUE_VER_MODIFICAR("La fila escogida está siendo utilizada. Inténtelo de nuevo más tarde."),
        CAMPO("Campo");
        // other String constants come here

        private String value;
        private StringConstant(String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
    }
	
	public enum ArrayConstant implements ConstantType {
		
		comboBoxTipoElementoEspecifico(new String []{"null","DATE","INT","VARCHAR(45)","VARCHAR(600)"});
        // other String constants come here

        private String[] value;
        private ArrayConstant(String[] value) {
            this.value = value;
        }
        public String[] value() {
            return value;
        }
    }
	
}
