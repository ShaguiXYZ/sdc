# Sistema de Tokens de Diseño

Este proyecto utiliza un sistema centralizado de tokens de diseño para gestionar colores, tipografía, espaciado y otros valores de diseño de manera consistente entre los temas claro y oscuro.

## 📁 Estructura de archivos

```
src/styles/
├── _tokens.scss          # Tokens centralizados y funciones
├── themes/
│   ├── light.scss        # Tema claro
│   └── dark.scss         # Tema oscuro
├── _colors.scss          # Colores específicos del proyecto
├── _core-colors.scss     # Colores base
└── _core-globals.scss    # Estilos globales
```

## 🎨 Usar tokens en tus componentes

### 1. Importar tokens en tu SCSS

```scss
@import '../../styles/tokens';

.mi-componente {
  background-color: gray(100); // Función para obtener color de escala de grises
  font-size: font-size(lg);     // Función para obtener tamaño de fuente
  padding: space(4);            // Función para obtener espaciado
  border-radius: radius(base);  // Función para obtener border radius
}
```

### 2. Usar variables CSS directamente

```scss
.mi-componente {
  background-color: var(--ui-01);
  color: var(--text-01);
  border: 1px solid var(--ui-04);
  box-shadow: var(--shadow-small);
}
```

### 3. Usar funciones de tokens

```scss
.mi-componente {
  // Colores de escala de grises
  background-color: gray(100);           // Tema claro por defecto
  background-color: gray(100, 'dark');   // Específico para tema oscuro
  
  // Tipografía
  font-size: font-size(lg);              // 18px
  line-height: line-height(lg);          // 28px
  
  // Espaciado
  margin: space(4);                      // 16px
  padding: space(6) space(4);            // 24px 16px
  
  // Border radius
  border-radius: radius(base);           // 4px
}
```

## 🎯 Variables CSS disponibles

### Colores base del sistema
- `--ui-01` a `--ui-06`: Colores de interfaz (del más claro al más oscuro)
- `--text-01`, `--text-02`: Colores de texto
- `--interactive-primary`, `--interactive-text`: Colores interactivos
- `--hover-primary`, `--hover-secondary`: Estados hover
- `--active-primary`: Estado activo

### Colores de estado
- `--success`: Verde para éxito
- `--warning`: Amarillo para advertencia  
- `--danger`: Rojo para error
- `--info`: Azul para información

### Colores de acento
- `--accent-01`: Naranja
- `--accent-02`: Verde

### Sombras
- `--shadow-small`: Sombra pequeña
- `--shadow-large`: Sombra grande

## 🔧 Crear nuevos tokens

### 1. Agregar al archivo `_tokens.scss`

```scss
// Agregar nuevo color a la escala
$my-custom-colors: (
  brand-primary: #ff6b35,
  brand-secondary: #004e89
);

// Agregar nueva función
@function brand-color($color) {
  @return map-get($my-custom-colors, $color);
}
```

### 2. Usar en mixins de tema

```scss
@mixin light-theme-colors() {
  // ... tokens existentes
  --brand-primary: #{brand-color(brand-primary)};
  --brand-secondary: #{brand-color(brand-secondary)};
}

@mixin dark-theme-colors() {
  // ... tokens existentes  
  --brand-primary: #{lighten(brand-color(brand-primary), 10%)};
  --brand-secondary: #{lighten(brand-color(brand-secondary), 15%)};
}
```

## 🌓 Cambiar entre temas

### En TypeScript/JavaScript
```typescript
// Cambiar a tema oscuro
document.documentElement.setAttribute('data-theme', 'dark');

// Cambiar a tema claro
document.documentElement.setAttribute('data-theme', 'light');

// Detectar preferencia del sistema
const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
document.documentElement.setAttribute('data-theme', prefersDark ? 'dark' : 'light');
```

### En Angular (servicio de tema)
```typescript
@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private currentTheme = 'light';

  setTheme(theme: 'light' | 'dark'): void {
    this.currentTheme = theme;
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
  }

  getTheme(): string {
    return this.currentTheme;
  }

  toggleTheme(): void {
    const newTheme = this.currentTheme === 'light' ? 'dark' : 'light';
    this.setTheme(newTheme);
  }
}
```

## 📝 Mejores prácticas

### ✅ Hacer
- Usar variables CSS para colores que cambien entre temas
- Usar funciones de tokens para valores que no cambien entre temas
- Crear componentes que se adapten automáticamente al tema
- Probar ambos temas durante el desarrollo

### ❌ Evitar
- Hardcodear colores en los componentes
- Usar valores absolutos para espaciado sin consultar los tokens
- Crear variables CSS custom sin seguir la convención de nombres
- Ignorar el contraste de colores en el tema oscuro

## 🎨 Paleta de colores

### Tema claro
- **Fondo principal**: `#ffffff` (`--ui-01`)
- **Fondo secundario**: `#f5f5f5` (`--ui-02`)
- **Texto principal**: `#414141` (`--text-01`)
- **Primario**: `#007ab3` (`--interactive-primary`)

### Tema oscuro  
- **Fondo principal**: `#1a1a1a` (`--ui-01`)
- **Fondo secundario**: `#2a2a2a` (`--ui-02`)
- **Texto principal**: `#e0e0e0` (`--text-01`)
- **Primario**: `#29b6f6` (`--interactive-primary`)

## 🔄 Actualizar tokens existentes

Para modificar tokens existentes:

1. **Edita `_tokens.scss`** para cambiar valores base
2. **Los temas se actualizarán automáticamente** gracias a los mixins
3. **Testa ambos temas** para asegurar que los cambios se ven bien
4. **Actualiza la documentación** si es necesario

## 🚀 Extensiones futuras

El sistema está preparado para:
- Agregar más temas (ej: modo alto contraste)
- Tokens para motion/animaciones
- Tokens para breakpoints responsive
- Tokens para componentes específicos
- Integración con design systems externos
