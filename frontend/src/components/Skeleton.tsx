// src/components/Skeleton.tsx
export const Skeleton: React.FC<{ width?: string; height?: string }> = ({ width = '100%', height = '1rem' }) => (
  <div className={`animate-pulse bg-gray-200`} style={{ width, height, borderRadius: 4 }} />
);


