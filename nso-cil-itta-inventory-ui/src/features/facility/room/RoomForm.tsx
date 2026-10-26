import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { ROOM_ROOM_TYPE_OPTIONS, ROOM_LIFECYCLE_STAGE_OPTIONS, RoomRoomType, RoomLifecycleStage } from './types';
import { validateRoom, cleanRoomValues } from './schema';
import { ROOM_DEFAULTS } from './types';
import type { RoomFormValues, RoomInput } from './types';

interface Props {
  initial?: RoomFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: RoomInput) => void;
  onCancel?: () => void;
}

export function RoomForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<RoomFormValues>({ ...ROOM_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof RoomFormValues>(key: K, value: RoomFormValues[K]) {
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
    const result = validateRoom(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanRoomValues(values as Record<string, unknown>) as unknown as RoomInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Room form">
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
      <FormField label="Floor Id" htmlFor="floorId" error={errors.floorId} required={true}>
        <Input
          id="floorId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.floorId ?? ''}
          onChange={(v) => set('floorId', v)}
        />
      </FormField>
      <FormField label="Room Type" htmlFor="roomType" error={errors.roomType} required={false}>
        <Select
          id="roomType"
          value={values.roomType ?? ''}
          options={ROOM_ROOM_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('roomType', (v || undefined) as RoomRoomType | undefined)}
        />
      </FormField>
      <FormField label="Area Sqm" htmlFor="areaSqm" error={errors.areaSqm} required={false}>
        <NumberInput
          id="areaSqm"
          value={values.areaSqm}
          step={0.0001}
          onChange={(v) => set('areaSqm', v)}
        />
      </FormField>
      <FormField label="Cooling Tons" htmlFor="coolingTons" error={errors.coolingTons} required={false}>
        <NumberInput
          id="coolingTons"
          value={values.coolingTons}
          step={0.0001}
          onChange={(v) => set('coolingTons', v)}
        />
      </FormField>
      <FormField label="Fire Zone" htmlFor="fireZone" error={errors.fireZone} required={false}>
        <Input
          id="fireZone"
          value={values.fireZone ?? ''}
          maxLength={255}
          onChange={(v) => set('fireZone', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
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
          options={ROOM_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as RoomLifecycleStage | undefined)}
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
