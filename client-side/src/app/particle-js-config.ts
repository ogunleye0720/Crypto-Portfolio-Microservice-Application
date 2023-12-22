export const ParticlesConfig = {
    particles: {
      number: {
        value: 50,
        density: {
          enable: true,
          value_area: 1400
        }
      },
      color: {
        value: '#E9E9E9'
      },
      shape: {
        type: 'polygon',
        stroke: {
          width: 1,
          color: '#E9E9E9'
        },
        polygon: {
          nb_sides: 5
        }
      },
      opacity: {
        value: 1,
        random: true,
        anim: {
          enable: true,
          speed: 0.7,
          opacity_min: 0.25,
          sync: true
        }
      },
      size: {
        value: 2,
        random: true,
        anim: {
          enable: true,
          speed: 10,
          size_min: 1.25,
          sync: true
        }
      },
      line_linked: {
        enable: true,
        distance: 150,
        color: '#E9E9E9',
        opacity: 1,
        width: 1
      },
      move: {
        enable: true,
        speed: 7,
        direction: 'none',
        random: true,
        straight: false,
        out_mode: 'out',
        bounce: true,
        attract: {
          enable: true,
          rotateX: 2000,
          rotateY: 2000
        }
      }
    },
    interactivity: {
      detect_on: 'window',
      events: {
        onhover: {
          enable: true,
          mode: 'grab'
        },
        onclick: {
          enable: true,
          mode: 'repulse'
        },
        resize: true
      },
      modes: {
        grab: {
          distance: 200,
          line_linked: {
            opacity: 3
          }
        },
        repulse: {
          distance: 250,
          duration: 2
        }
      }
    },
    retina_detect: false
 };