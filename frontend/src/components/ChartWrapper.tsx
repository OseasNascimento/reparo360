import React from 'react';

type ChartWrapperProps = {
  title: string;
  children: React.ReactNode;
  actions?: React.ReactNode; // opcional (filtros, botões)
};

export const ChartWrapper: React.FC<ChartWrapperProps> = ({ title, children, actions }) => {
  return (
    <section
      className="
        rounded-2xl border border-slate-200/70 bg-white/80 backdrop-blur
        shadow-[0_10px_30px_-12px_rgba(2,6,23,0.15)] hover:shadow-[0_18px_36px_-10px_rgba(2,6,23,0.2)]
        transition-shadow duration-300
      "
    >
      <div className="flex items-center justify-between px-5 pt-4">
        <h3 className="text-[15px] font-semibold text-slate-800">{title}</h3>
        {actions ? <div className="flex items-center gap-2">{actions}</div> : null}
      </div>
      <div className="px-2 sm:px-3 pb-4 pt-2">{children}</div>
    </section>
  );
};

export default ChartWrapper;
