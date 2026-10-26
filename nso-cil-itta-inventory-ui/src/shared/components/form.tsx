import type { ReactNode } from 'react';

export function FormField(props: { label: string; htmlFor: string; error?: string; required?: boolean; children: ReactNode }) {
  const { label, htmlFor, error, required, children } = props;
  return (
    <div className={`form-field${error ? ' form-field--error' : ''}`}>
      <label htmlFor={htmlFor}>
        {label}
        {required ? <span aria-hidden="true"> *</span> : null}
      </label>
      {children}
      {error ? (
        <p className="form-field__error" id={`${htmlFor}-error`}>
          {error}
        </p>
      ) : null}
    </div>
  );
}

interface TextProps {
  id: string;
  value: string;
  onChange: (value: string) => void;
  maxLength?: number;
  placeholder?: string;
  'aria-label'?: string;
}

export function Input({ id, value, onChange, maxLength, placeholder, ...rest }: Omit<TextProps, 'id'> & { id?: string }) {
  return (
    <input
      id={id}
      className="input"
      type="text"
      value={value}
      maxLength={maxLength}
      placeholder={placeholder}
      onChange={(ev) => onChange(ev.target.value)}
      {...rest}
    />
  );
}

export function TextArea({ id, value, onChange, maxLength, rows = 3 }: TextProps & { rows?: number }) {
  return (
    <textarea
      id={id}
      className="input input--area"
      value={value}
      rows={rows}
      maxLength={maxLength}
      onChange={(ev) => onChange(ev.target.value)}
    />
  );
}

export function NumberInput(props: { id: string; value?: number; step?: number; onChange: (value: number | undefined) => void }) {
  const { id, value, step = 1, onChange } = props;
  return (
    <input
      id={id}
      className="input"
      type="number"
      step={step}
      value={value ?? ''}
      onChange={(ev) => onChange(ev.target.value === '' ? undefined : Number(ev.target.value))}
    />
  );
}

export function Checkbox(props: { id: string; checked: boolean; onChange: (value: boolean) => void }) {
  const { id, checked, onChange } = props;
  return <input id={id} type="checkbox" checked={checked} onChange={(ev) => onChange(ev.target.checked)} />;
}

export function DateInput(props: { id: string; value: string; onChange: (value: string) => void }) {
  const { id, value, onChange } = props;
  return <input id={id} className="input" type="date" value={value} onChange={(ev) => onChange(ev.target.value)} />;
}

export function DateTimeInput(props: { id: string; value: string; onChange: (value: string) => void }) {
  const { id, value, onChange } = props;
  const local = value ? value.replace('Z', '').slice(0, 16) : '';
  return (
    <input
      id={id}
      className="input"
      type="datetime-local"
      value={local}
      onChange={(ev) => onChange(ev.target.value ? `${ev.target.value}:00Z` : '')}
    />
  );
}

export interface Option<V extends string = string> {
  value: V;
  label: string;
}

export function Select(props: {
  id: string;
  value: string;
  options: readonly Option[];
  allowEmpty?: boolean;
  onChange: (value: string) => void;
}) {
  const { id, value, options, allowEmpty = true, onChange } = props;
  return (
    <select id={id} className="input" value={value} onChange={(ev) => onChange(ev.target.value)}>
      {allowEmpty ? <option value="">—</option> : null}
      {options.map((o) => (
        <option key={o.value} value={o.value}>
          {o.label}
        </option>
      ))}
    </select>
  );
}
