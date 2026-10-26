import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { ANTENNA_CRITICALITY_OPTIONS, AntennaCriticality } from './types';
import { validateAntenna, cleanAntennaValues } from './schema';
import { ANTENNA_DEFAULTS } from './types';
import type { AntennaFormValues, AntennaInput } from './types';

interface Props {
  initial?: AntennaFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: AntennaInput) => void;
  onCancel?: () => void;
}

export function AntennaForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<AntennaFormValues>({ ...ANTENNA_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof AntennaFormValues>(key: K, value: AntennaFormValues[K]) {
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
    const result = validateAntenna(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanAntennaValues(values as Record<string, unknown>) as unknown as AntennaInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Antenna form">
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
      <FormField label="Sector Id" htmlFor="sectorId" error={errors.sectorId} required={true}>
        <Input
          id="sectorId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.sectorId ?? ''}
          onChange={(v) => set('sectorId', v)}
        />
      </FormField>
      <FormField label="Model" htmlFor="model" error={errors.model} required={false}>
        <Input
          id="model"
          value={values.model ?? ''}
          maxLength={255}
          onChange={(v) => set('model', v)}
        />
      </FormField>
      <FormField label="Height M" htmlFor="heightM" error={errors.heightM} required={false}>
        <NumberInput
          id="heightM"
          value={values.heightM}
          step={0.0001}
          onChange={(v) => set('heightM', v)}
        />
      </FormField>
      <FormField label="Azimuth Deg" htmlFor="azimuthDeg" error={errors.azimuthDeg} required={false}>
        <NumberInput
          id="azimuthDeg"
          value={values.azimuthDeg}
          step={0.0001}
          onChange={(v) => set('azimuthDeg', v)}
        />
      </FormField>
      <FormField label="Tilt Deg" htmlFor="tiltDeg" error={errors.tiltDeg} required={false}>
        <NumberInput
          id="tiltDeg"
          value={values.tiltDeg}
          step={0.0001}
          onChange={(v) => set('tiltDeg', v)}
        />
      </FormField>
      <FormField label="Gain Dbi" htmlFor="gainDbi" error={errors.gainDbi} required={false}>
        <NumberInput
          id="gainDbi"
          value={values.gainDbi}
          step={0.0001}
          onChange={(v) => set('gainDbi', v)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
          options={ANTENNA_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as AntennaCriticality | undefined)}
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
