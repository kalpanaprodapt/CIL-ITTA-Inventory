import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { FIBER_STRAND_CRITICALITY_OPTIONS, FiberStrandCriticality } from './types';
import { validateFiberStrand, cleanFiberStrandValues } from './schema';
import { FIBERSTRAND_DEFAULTS } from './types';
import type { FiberStrandFormValues, FiberStrandInput } from './types';

interface Props {
  initial?: FiberStrandFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: FiberStrandInput) => void;
  onCancel?: () => void;
}

export function FiberStrandForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<FiberStrandFormValues>({ ...FIBERSTRAND_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof FiberStrandFormValues>(key: K, value: FiberStrandFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateFiberStrand(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanFiberStrandValues(values as Record<string, unknown>) as unknown as FiberStrandInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Fiber Strand form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Cable Id" htmlFor="cableId" error={errors.cableId} required={true}>
        <Input
          id="cableId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.cableId ?? ''}
          onChange={(v) => set('cableId', v)}
        />
      </FormField>
      <FormField label="Strand Number" htmlFor="strandNumber" error={errors.strandNumber} required={true}>
        <NumberInput
          id="strandNumber"
          value={values.strandNumber}
          step={1}
          onChange={(v) => set('strandNumber', v)}
        />
      </FormField>
      <FormField label="Buffer Tube" htmlFor="bufferTube" error={errors.bufferTube} required={false}>
        <Input
          id="bufferTube"
          value={values.bufferTube ?? ''}
          maxLength={255}
          onChange={(v) => set('bufferTube', v)}
        />
      </FormField>
      <FormField label="Tube Color" htmlFor="tubeColor" error={errors.tubeColor} required={false}>
        <Input
          id="tubeColor"
          value={values.tubeColor ?? ''}
          maxLength={255}
          onChange={(v) => set('tubeColor', v)}
        />
      </FormField>
      <FormField label="Attenuation Db" htmlFor="attenuationDb" error={errors.attenuationDb} required={false}>
        <NumberInput
          id="attenuationDb"
          value={values.attenuationDb}
          step={0.0001}
          onChange={(v) => set('attenuationDb', v)}
        />
      </FormField>
      <FormField label="In Use" htmlFor="inUse" error={errors.inUse} required={false}>
        <Checkbox
          id="inUse"
          checked={Boolean(values.inUse)}
          onChange={(v) => set('inUse', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={FIBER_STRAND_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as FiberStrandCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
