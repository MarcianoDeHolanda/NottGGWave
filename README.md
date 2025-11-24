# NottGGWave

Plugin de Minecraft para Paper/Spigot 1.20.4 que gestiona GG Waves con recompensas personalizables y efectos visuales dinámicos.

## 🌟 Características

- **🎮 Comandos simples**: `/nottggwave start` y `/nottggwave reload`
- **⏱️ Duración configurable**: Define cuánto tiempo dura la wave
- **🌈 Formatos GG rotativos**: 8 gradientes de colores que rotan cíclicamente
- **💬 Mensajes personalizados**: Usa MiniMessage para colores y formatos avanzados
- **🔄 Participación ilimitada**: Los jugadores pueden escribir GG múltiples veces
- **🎁 Recompensa única**: Solo la primera participación da recompensas
- **✨ Efectos opcionales**: Sonidos, partículas y recompensas personalizables
- **🔧 Compatible**: Funciona con Java 17+ y Minecraft 1.20.4

---

## 📦 Instalación

1. **Descarga** el archivo `.jar` desde [Releases](https://github.com/MarcianoDeHolanda/NottGGWave/releases)
2. **Copia** el JAR a la carpeta `plugins` de tu servidor
3. **Reinicia** o recarga el servidor
4. **Configura** el archivo `plugins/NottGGWave/config.yml` según tus preferencias

---

## 🎮 Comandos

| Comando | Permiso | Descripción |
|---------|---------|-------------|
| `/nottggwave start` | `nottggwave.admin` | Inicia una nueva GG Wave |
| `/nottggwave reload` | `nottggwave.admin` | Recarga la configuración |

---

## 🔐 Permisos

| Permiso | Defecto | Descripción |
|---------|---------|-------------|
| `nottggwave.admin` | OP | Permite administrar GG Waves |
| `nottggwave.use` | Todos | Permite participar en GG Waves |

---

## ⚙️ Configuración

### **Configuración Principal** (`config.yml`)

```yaml
ggwave:
  # Duración de la GG Wave en segundos
  duration: 10
  
  # Mensaje de inicio (MiniMessage)
  start-message: "<green>Empezó el GG Wave, pon GG en el chat para ganar recompensas!"
  
  # Sonido de inicio
  start-sound: "item.goat.horn.sound.0"
  start-sound-volume: 1.0
  start-sound-pitch: 1.0
  
  # Formatos GG rotativos (se ciclan automáticamente)
  gg-formats:
    - "<gradient:red:yellow><b>%player_name% » GG!</gradient>"
    - "<gradient:blue:green><b>%player_name% » GG!</gradient>"
    - "<gradient:purple:pink><b>%player_name% » GG!</gradient>"
    - "<gradient:orange:red><b>%player_name% » GG!</gradient>"
    - "<gradient:cyan:blue><b>%player_name% » GG!</gradient>"
    - "<gradient:yellow:orange><b>%player_name% » GG!</gradient>"
    - "<gradient:green:blue><b>%player_name% » GG!</gradient>"
    - "<gradient:pink:purple><b>%player_name% » GG!</gradient>"

  # Efectos para la primera participación
  effects:
    sound: "entity.player.levelup"
    sound-volume: 1.0
    sound-pitch: 1.0
    particle: "VILLAGER_HAPPY"
    particle-count: 10
    player-message: "<gold>¡Gracias por participar en la GG Wave!"

# Recompensas (solo primera participación)
rewards:
  commands:
    - "give %player_name% minecraft:diamond 1"
    - "say %player_name% participó en la GG Wave!"
```

---

## 🔄 Cómo Funciona

1. **🎯 Inicio**: Admin ejecuta `/nottggwave start`
2. **📢 Anuncio**: Mensaje global + sonido de inicio
3. **🌈 Participación**: Los jugadores escriben "GG" y ven mensajes con gradientes rotativos
4. **🎁 Recompensa**: Solo el primer GG de cada jugador da recompensas
5. **✨ Efectos**: Sonidos y partículas en la primera participación
6. **🔄 Continuidad**: Los jugadores pueden seguir escribiendo GG con diferentes colores

---

## 🎨 Variables Disponibles

| Variable | Uso | Descripción |
|----------|-----|-------------|
| `%player_name%` | `gg-formats`, `rewards.commands` | Nombre del jugador |

---

## 🛠️ Desarrollo

El plugin sigue las mejores prácticas:

- **📁 Arquitectura limpia**: Separación por paquetes (commands, listeners, managers)
- **🔄 Eventos asíncronos**: Manejo correcto de AsyncChatEvent
- **📡 APIs oficiales**: Uso de Paper API y Adventure MiniMessage
- **🎛️ Configuración flexible**: Sistema YAML robusto
- **🏗️ Patrones SOLID**: Inyección de dependencias y Singleton

---

## 📋 Requisitos

- **Minecraft**: Paper/Spigot 1.20.4
- **Java**: 17 o superior
- **Dependencias**: Adventure API (incluida en Paper)

---

## 🌐 English Version

---

# NottGGWave

Minecraft plugin for Paper/Spigot 1.20.4 that manages GG Waves with customizable rewards and dynamic visual effects.

## 🌟 Features

- **🎮 Simple Commands**: `/nottggwave start` and `/nottggwave reload`
- **⏱️ Configurable Duration**: Set how long the wave lasts
- **🌈 Rotating GG Formats**: 8 color gradients that cycle automatically
- **💬 Custom Messages**: Use MiniMessage for colors and advanced formatting
- **🔄 Unlimited Participation**: Players can write GG multiple times
- **🎁 Unique Rewards**: Only first participation gives rewards
- **✨ Optional Effects**: Sounds, particles, and customizable rewards
- **🔧 Compatible**: Works with Java 17+ and Minecraft 1.20.4

---

## 📦 Installation

1. **Download** the `.jar` file from [Releases](https://github.com/MarcianoDeHolanda/NottGGWave/releases)
2. **Copy** the JAR to your server's `plugins` folder
3. **Restart** or reload the server
4. **Configure** the `plugins/NottGGWave/config.yml` file to your preferences

---

## 🎮 Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/nottggwave start` | `nottggwave.admin` | Start a new GG Wave |
| `/nottggwave reload` | `nottggwave.admin` | Reload configuration |

---

## 🔐 Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `nottggwave.admin` | OP | Allow managing GG Waves |
| `nottggwave.use` | Everyone | Allow participating in GG Waves |

---

## ⚙️ Configuration

### **Main Configuration** (`config.yml`)

```yaml
ggwave:
  # Duration of GG Wave in seconds
  duration: 10
  
  # Start message (MiniMessage)
  start-message: "<green>GG Wave started! Type GG in chat to win rewards!"
  
  # Start sound
  start-sound: "item.goat.horn.sound.0"
  start-sound-volume: 1.0
  start-sound-pitch: 1.0
  
  # Rotating GG formats (auto-cycle)
  gg-formats:
    - "<gradient:red:yellow><b>%player_name% » GG!</gradient>"
    - "<gradient:blue:green><b>%player_name% » GG!</gradient>"
    - "<gradient:purple:pink><b>%player_name% » GG!</gradient>"
    - "<gradient:orange:red><b>%player_name% » GG!</gradient>"
    - "<gradient:cyan:blue><b>%player_name% » GG!</gradient>"
    - "<gradient:yellow:orange><b>%player_name% » GG!</gradient>"
    - "<gradient:green:blue><b>%player_name% » GG!</gradient>"
    - "<gradient:pink:purple><b>%player_name% » GG!</gradient>"

  # Effects for first participation
  effects:
    sound: "entity.player.levelup"
    sound-volume: 1.0
    sound-pitch: 1.0
    particle: "VILLAGER_HAPPY"
    particle-count: 10
    player-message: "<gold>Thanks for participating in the GG Wave!"

# Rewards (first participation only)
rewards:
  commands:
    - "give %player_name% minecraft:diamond 1"
    - "say %player_name% participated in the GG Wave!"
```

---

## 🔄 How It Works

1. **🎯 Start**: Admin executes `/nottggwave start`
2. **📢 Announcement**: Global message + start sound
3. **🌈 Participation**: Players type "GG" and see rotating gradient messages
4. **🎁 Reward**: Only first GG of each player gives rewards
5. **✨ Effects**: Sounds and particles on first participation
6. **🔄 Continuity**: Players can keep writing GG with different colors

---

## 🎨 Available Variables

| Variable | Usage | Description |
|----------|-------|-------------|
| `%player_name%` | `gg-formats`, `rewards.commands` | Player name |

---

## 🛠️ Development

The plugin follows best practices:

- **📁 Clean Architecture**: Package separation (commands, listeners, managers)
- **🔄 Async Events**: Proper AsyncChatEvent handling
- **📡 Official APIs**: Paper API and Adventure MiniMessage usage
- **🎛️ Flexible Config**: Robust YAML system
- **🏗️ SOLID Patterns**: Dependency injection and Singleton

---

## 📋 Requirements

- **Minecraft**: Paper/Spigot 1.20.4
- **Java**: 17 or higher
- **Dependencies**: Adventure API (included in Paper)

---

## 👤 Author

Created by **nottabaker**

---

## 🙏 Acknowledgments

This plugin was inspired by **DrakoWave** - a GG Wave management system. NottGGWave is a complete rewrite with enhanced features, modern APIs, and additional functionality while maintaining the core concept of community GG Waves.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## 🐛 Issues

Found a bug? Please report it on [Issues](https://github.com/MarcianoDeHolanda/NottGGWave/issues).
