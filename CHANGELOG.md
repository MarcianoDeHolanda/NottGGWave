# Changelog

All notable changes to NottGGWave will be documented in this file.

## [1.0.1] - 2024-11-24

### 🐛 Fixed
- **%player_name% placeholder**: Fixed issue where player names were not being replaced correctly
- **Gradient rendering**: Fixed MiniMessage gradient formatting not displaying properly
- **Performance optimization**: Restored message precomputing while maintaining functionality

### ⚡ Improved
- **Message processing**: Optimized to precompute format strings for better performance
- **Placeholder replacement**: Implemented reliable string replacement method
- **Code quality**: Cleaned up unused imports and fields

### 🔧 Technical Changes
- **GGWaveManager**: Refactored to use precomputed format strings
- **ChatListener**: Maintained AsyncPlayerChatEvent with proper documentation
- **Build system**: Updated to version 1.0.1

### 🎨 Visual Enhancements
- **Custom gradients**: Support for hex color gradients in config
- **Rich formatting**: Bold, underline, italic text formatting
- **Rotating formats**: 8 different gradient combinations that cycle automatically

---

## [1.0.0] - 2024-11-24

### 🎉 Initial Release

### 🌟 Features
- **GG Wave Management**: Complete system for managing GG Waves
- **Rotating Gradients**: 8 beautiful color gradients that cycle automatically
- **Configurable Duration**: Set custom wave duration (default: 10 seconds)
- **Visual Effects**: Sounds and particles for player participation
- **Reward System**: Customizable commands for first-time participants
- **Unlimited Participation**: Players can write GG multiple times with different colors
- **Bilingual Support**: Full Spanish and English documentation

### 🎮 Commands
- `/nottggwave start` - Start a new GG Wave
- `/nottggwave reload` - Reload configuration

### 🔐 Permissions
- `nottggwave.admin` - Manage GG Waves (default: OP)
- `nottggwave.use` - Participate in GG Waves (default: everyone)

### 📋 Requirements
- **Minecraft**: Paper/Spigot 1.20.4
- **Java**: 17 or higher

### 🎨 Default Configuration
- **Duration**: 10 seconds
- **Start Sound**: Goat Horn
- **Participation Sound**: Level Up
- **Particles**: Villager Happy
- **Reward**: 1 Diamond per player (first participation only)

---

## 🙏 Acknowledgments

- **DrakoWave**: Original inspiration for GG Wave management system
- **Adventure API**: Beautiful text component system that makes gradients possible
- **PaperMC**: Excellent platform for plugin development

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
