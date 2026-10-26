import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SECTOR_CRITICALITY_OPTIONS, SectorCriticality } from './types';
import { validateSector, cleanSectorValues } from './schema';
import { SECTOR_DEFAULTS } from './types';
import type { SectorFormValues, SectorInput } from './types';

interface Props {
  initial?: SectorFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SectorInput) => void;
  onCancel?: () => void;
}

export function SectorForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SectorFormValues>({ ...SECTOR_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SectorFormValues>(key: K, value: SectorFormValues[K]) {
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
    const result = validateSector(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSectorValues(values as Record<string, unknown>) as unknown as SectorInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Sector form">
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
      <FormField label="Base Station Id" htmlFor="baseStationId" error={errors.baseStationId} required={true}>
        <Input
          id="baseStationId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.baseStationId ?? ''}
          onChange={(v) => set('baseStationId', v)}
        />
      </FormField>
      <FormField label="Sector Number" htmlFor="sectorNumber" error={errors.sectorNumber} required={true}>
        <NumberInput
          id="sectorNumber"
          value={values.sectorNumber}
          step={1}
          onChange={(v) => set('sectorNumber', v)}
        />
      </FormField>
      <FormField label="Pci" htmlFor="pci" error={errors.pci} required={false}>
        <NumberInput
          id="pci"
          value={values.pci}
          step={1}
          onChange={(v) => set('pci', v)}
        />
      </FormField>
      <FormField label="Frequency Mhz" htmlFor="frequencyMhz" error={errors.frequencyMhz} required={false}>
        <NumberInput
          id="frequencyMhz"
          value={values.frequencyMhz}
          step={1}
          onChange={(v) => set('frequencyMhz', v)}
        />
      </FormField>
      <FormField label="Bandwidth Mhz" htmlFor="bandwidthMhz" error={errors.bandwidthMhz} required={false}>
        <NumberInput
          id="bandwidthMhz"
          value={values.bandwidthMhz}
          step={1}
          onChange={(v) => set('bandwidthMhz', v)}
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
          options={SECTOR_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as SectorCriticality | undefined)}
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
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
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
