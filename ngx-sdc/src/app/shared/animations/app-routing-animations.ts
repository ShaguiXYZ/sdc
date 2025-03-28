import { AnimationMetadata, animate, animateChild, group, query, style, transition, trigger } from '@angular/animations';
import { AppUrls } from '../config/routing';

const horizontalSlideTo = (direction: 'left' | 'right'): AnimationMetadata[] => {
  const optional = { optional: true };

  return [
    style({ position: 'relative' }),
    query(
      ':enter, :leave',
      [
        style({
          position: 'absolute',
          top: 0,
          [direction]: 0,
          width: '100%'
        })
      ],
      { optional: true }
    ),
    query(':enter', [style({ [direction]: '-100%' })]),
    query(':leave', animateChild(), { optional: true }),
    group([
      query(':leave', [animate('200ms ease', style({ [direction]: '100%' }))], optional),
      query(':enter', [animate('200ms ease', style({ [direction]: '0%' }))], optional)
    ])
  ];
};

const animationFade: AnimationMetadata[] = [
  query(
    ':enter',
    [
      style({
        opacity: 0,
        position: 'absolute',
        height: '100%',
        width: '100%'
      })
    ],
    { optional: true }
  ),
  query(
    ':leave',
    // here we apply a style and use the animate function to apply the style over 0.3 seconds
    [
      style({
        opacity: 1,
        position: 'absolute',
        height: '100%',
        width: '100%'
      }),
      animate('200ms ease', style({ opacity: 0 }))
    ],
    { optional: true }
  ),
  query(
    ':enter',
    [
      style({
        opacity: 0,
        position: 'relative',
        height: '100%',
        width: '100%'
      }),
      animate('200ms ease', style({ opacity: 1 }))
    ],
    { optional: true }
  )
];

export const routingAnimation = trigger('routeAnimations', [
  transition(`* => ${AppUrls.metrics}`, horizontalSlideTo('right')),
  transition(`${AppUrls.metrics} => *`, horizontalSlideTo('left')),
  transition(`${AppUrls.departments} => *`, horizontalSlideTo('right')),
  transition(`${AppUrls.squads} => ${AppUrls.departments}`, horizontalSlideTo('left')),
  transition(`${AppUrls.squads} => ${AppUrls.applications}`, horizontalSlideTo('right')),
  transition(`${AppUrls.applications} => ${AppUrls.squads}`, horizontalSlideTo('left')),
  transition(`${AppUrls.applications} => *`, horizontalSlideTo('left')),
  transition('* <=> *', animationFade)
]);
