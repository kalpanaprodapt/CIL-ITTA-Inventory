import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { RACK_UNIT_FACE_OPTIONS, RACK_UNIT_CRITICALITY_OPTIONS, RackUnitFace, RackUnitCriticality } from './types';
import { validateRackUnit, cleanRackUnitValues } from './schema';
import { RACKUNIT_DEFAULTS } from './types';
import type { RackUnitFormValues, RackUnitInput } from './types';

interface Props {
  initial?: RackUnitFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: RackUnitInput) => void;
  onCancel?: () => void;
}

export function RackUnitForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<RackUnitFormValues>({ ...RACKUNIT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof RackUnitFormValues>(key: K, value: RackUnitFormValues[K]) {
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
    const result = validateRackUnit(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanRackUnitValues(values as Record<string, unknown>) as unknown as RackUnitInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Rack Unit form">
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
      <FormField label="Rack Id" htmlFor="rackId" error={errors.rackId} required={true}>
        <Input
          id="rackId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.rackId ?? ''}
          onChange={(v) => set('rackId', v)}
        />
      </FormField>
      <FormField label="Unit Number" htmlFor="unitNumber" error={errors.unitNumber} required={true}>
        <NumberInput
          id="unitNumber"
          value={values.unitNumber}
          step={1}
          onChange={(v) => set('unitNumber', v)}
        />
      </FormField>
      <FormField label="Occupied" htmlFor="occupied" error={errors.occupied} required={false}>
        <Checkbox
          id="occupied"
          checked={Boolean(values.occupied)}
          onChange={(v) => set('occupied', v)}
        />
      </FormField>
      <FormField label="Mounted Device Id" htmlFor="mountedDeviceId" error={errors.mountedDeviceId} required={false}>
        <Input
          id="mountedDeviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.mountedDeviceId ?? ''}
          onChange={(v) => set('mountedDeviceId', v)}
        />
      </FormField>
      <FormField label="Face" htmlFor="face" error={errors.face} required={false}>
        <Select
          id="face"
          value={values.face ?? ''}
          options={RACK_UNIT_FACE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('face', (v || undefined) as RackUnitFace | undefined)}
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
          options={RACK_UNIT_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as RackUnitCriticality | undefined)}
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
