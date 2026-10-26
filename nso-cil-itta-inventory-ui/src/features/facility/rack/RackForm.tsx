import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { RACK_LIFECYCLE_STAGE_OPTIONS, RackLifecycleStage } from './types';
import { validateRack, cleanRackValues } from './schema';
import { RACK_DEFAULTS } from './types';
import type { RackFormValues, RackInput } from './types';

interface Props {
  initial?: RackFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: RackInput) => void;
  onCancel?: () => void;
}

export function RackForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<RackFormValues>({ ...RACK_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof RackFormValues>(key: K, value: RackFormValues[K]) {
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
    const result = validateRack(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanRackValues(values as Record<string, unknown>) as unknown as RackInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Rack form">
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
      <FormField label="Room Id" htmlFor="roomId" error={errors.roomId} required={true}>
        <Input
          id="roomId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.roomId ?? ''}
          onChange={(v) => set('roomId', v)}
        />
      </FormField>
      <FormField label="Rack Units" htmlFor="rackUnits" error={errors.rackUnits} required={true}>
        <NumberInput
          id="rackUnits"
          value={values.rackUnits}
          step={1}
          onChange={(v) => set('rackUnits', v)}
        />
      </FormField>
      <FormField label="Width Mm" htmlFor="widthMm" error={errors.widthMm} required={false}>
        <NumberInput
          id="widthMm"
          value={values.widthMm}
          step={1}
          onChange={(v) => set('widthMm', v)}
        />
      </FormField>
      <FormField label="Depth Mm" htmlFor="depthMm" error={errors.depthMm} required={false}>
        <NumberInput
          id="depthMm"
          value={values.depthMm}
          step={1}
          onChange={(v) => set('depthMm', v)}
        />
      </FormField>
      <FormField label="Max Weight Kg" htmlFor="maxWeightKg" error={errors.maxWeightKg} required={false}>
        <NumberInput
          id="maxWeightKg"
          value={values.maxWeightKg}
          step={0.0001}
          onChange={(v) => set('maxWeightKg', v)}
        />
      </FormField>
      <FormField label="Row Label" htmlFor="rowLabel" error={errors.rowLabel} required={false}>
        <Input
          id="rowLabel"
          value={values.rowLabel ?? ''}
          maxLength={255}
          onChange={(v) => set('rowLabel', v)}
        />
      </FormField>
      <FormField label="Position" htmlFor="position" error={errors.position} required={false}>
        <Input
          id="position"
          value={values.position ?? ''}
          maxLength={255}
          onChange={(v) => set('position', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={RACK_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as RackLifecycleStage | undefined)}
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
